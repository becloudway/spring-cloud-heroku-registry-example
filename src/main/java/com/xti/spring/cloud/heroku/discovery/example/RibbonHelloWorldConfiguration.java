package com.xti.spring.cloud.heroku.discovery.example;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;

public class RibbonHelloWorldConfiguration {

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        return new RandomRule();
    }
}
