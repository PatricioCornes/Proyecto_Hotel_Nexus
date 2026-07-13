package com.nexus.eureka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "server.port=0")
class NexusEurekaServerApplicationTests {
    @Test void contextLoads() {}
}
