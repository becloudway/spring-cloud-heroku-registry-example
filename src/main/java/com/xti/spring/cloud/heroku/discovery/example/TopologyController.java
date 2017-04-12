package com.xti.spring.cloud.heroku.discovery.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping(path = "/topology")
public class TopologyController {

    @RequestMapping(method = RequestMethod.GET)
    public String getSpaceTopology(){
        try {
            return new String(Files.readAllBytes(Paths.get("/etc/heroku/space-topology.json")));
        } catch (IOException e) {
            e.printStackTrace();
            return "failed to get space topology";
        }

    }
}
