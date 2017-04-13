package com.xti.spring.cloud.heroku.discovery.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/topology")
public class TopologyController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(path = "/file", method = RequestMethod.GET)
    public String getSpaceTopology(){
        try {
            return new String(Files.readAllBytes(Paths.get("/etc/heroku/space-topology.json")));
        } catch (IOException e) {
            e.printStackTrace();
            return "failed to get space topology";
        }

    }

    @RequestMapping(path = "/springcloud", method = RequestMethod.GET)
    public String getSpringCloudTopology(){
        StringBuilder sb = new StringBuilder();
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            sb.append(service).append("\n");
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                sb.append(instance.getUri().toString()).append("\n");
                Map<String, String> metadata = instance.getMetadata();
                for (Map.Entry<String, String> entry : metadata.entrySet()) {
                    sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
                }
                sb.append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
