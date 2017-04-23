package com.xti.spring.cloud.heroku.discovery.example;

import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.apache.catalina.connector.Connector;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.CommandBusConnector;
import org.axonframework.commandhandling.distributed.CommandRouter;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.messaging.interceptors.LoggingInterceptor;
import org.axonframework.monitoring.NoOpMessageMonitor;
import org.axonframework.serialization.Serializer;
import org.axonframework.springcloud.commandhandling.SpringCloudCommandRouter;
import org.axonframework.springcloud.commandhandling.SpringHttpCommandBusConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableDiscoveryClient
public class DistributedAxonConfig {

    @Value("${server.port}")
    private String serverPort;

    @Value("${management.port:${server.port}}")
    private String managementPort;

    @Value("${server.additionalPorts:null}")
    private String additionalPorts;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        Connector[] additionalConnectors = this.additionalConnector();
        if (additionalConnectors != null && additionalConnectors.length > 0) {
            tomcat.addAdditionalTomcatConnectors(additionalConnectors);
        }
        return tomcat;
    }

    private Connector[] additionalConnector() {
        if (StringUtils.isBlank(this.additionalPorts)) {
            return null;
        }
        Set<String> defaultPorts = new HashSet<>(Arrays.asList(this.serverPort, this.managementPort));
        String[] ports = this.additionalPorts.split(",");
        List<Connector> result = new ArrayList<>();
        for (String port : ports) {
            if (!defaultPorts.contains(port)) {
                Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
                connector.setScheme("http");
                connector.setPort(Integer.valueOf(port));
                result.add(connector);
            }
        }
        return result.toArray(new Connector[result.size()]);
    }

    @Qualifier("localSegment")
    @Bean
    public CommandBus localSegment(TransactionManager transactionManager) {
        SimpleCommandBus localSegment = new
                SimpleCommandBus(transactionManager, NoOpMessageMonitor.INSTANCE);
        localSegment.registerDispatchInterceptor(new
                BeanValidationInterceptor<>());
        localSegment.registerHandlerInterceptor(new LoggingInterceptor<>());
        return localSegment;
    }

    @Bean
    public CommandRouter springCloudCommandRouter(DiscoveryClient discoveryClient) {
        return new SpringCloudCommandRouter(discoveryClient, new AnnotationRoutingStrategy());
    }

    @Bean
    public CommandBusConnector springHttpCommandBusConnector(@Qualifier("localSegment") CommandBus localSegment,
                                                             RestOperations restTemplate,
                                                             Serializer serializer) {
        return new SpringHttpCommandBusConnector(localSegment, restTemplate, serializer);
    }

    @Primary // to make sure this CommandBus implementation is used for autowiring
    @Bean
    public DistributedCommandBus springCloudDistributedCommandBus(CommandRouter commandRouter,
                                                                  CommandBusConnector commandBusConnector) {
        return new DistributedCommandBus(commandRouter, commandBusConnector);
    }

    @Bean
    public EventStore eventStore()  {
        return new EmbeddedEventStore(new InMemoryEventStorageEngine());
    }

    @Bean
    public Repository<NoteAggregate> noteCommandRepository()  {
        return new EventSourcingRepository<NoteAggregate>(NoteAggregate.class, eventStore());
    }


    @Bean
    IClientConfig iClientConfig(){
        return new DefaultClientConfigImpl();
    }


    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
