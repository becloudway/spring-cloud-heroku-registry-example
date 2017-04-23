package com.xti.spring.cloud.heroku.discovery.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication(scanBasePackages = {"com.xti.spring.cloud.heroku.discovery.example"})
@RibbonClient(name = "helloworldclient", configuration = RibbonHelloWorldConfiguration.class)
public class DistributedAxonStarter {

    public static void main(String[] args) {
        SpringApplication.run(DistributedAxonStarter.class, args);

    }
}
