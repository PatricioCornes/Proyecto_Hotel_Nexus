package com.Nexus_hospitality_payments.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Component
public class ServiceUrlResolver {
    private final DiscoveryClient discoveryClient;

    public ServiceUrlResolver(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public String resolve(String serviceId) {
        return discoveryClient.getInstances(serviceId).stream()
                .findFirst()
                .map(ServiceInstance::getUri)
                .map(Object::toString)
                .orElseThrow(() -> new IllegalStateException("No hay instancias disponibles de " + serviceId));
    }
}
