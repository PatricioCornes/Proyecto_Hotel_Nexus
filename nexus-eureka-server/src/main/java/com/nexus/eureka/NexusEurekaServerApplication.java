package com.nexus.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class NexusEurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(NexusEurekaServerApplication.class, args);
    }
}
