package com.xti.spring.cloud.heroku.discovery.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.xti.spring.cloud.heroku.discovery.example"})
public class DistributedAxonStarter {

    public static void main(String[] args) {
        SpringApplication.run(DistributedAxonStarter.class, args);

    }
}
