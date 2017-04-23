package com.xti.spring.cloud.heroku.discovery.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "/loadbalancer")
public class LoadBalancerController {

    @Value("${SPRING_CLOUD_HEROKU_PORT}")
    private String port;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(path = "/hello-local", method = RequestMethod.GET)
    public String getHelloLocal(){
        return "Hello from " + System.getenv("HEROKU_PRIVATE_IP");
    }

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String getHello(){
        return restTemplate.getForObject("http://web.cloudapp:" + port + "/loadbalancer/hello-local", String.class);
    }
}
