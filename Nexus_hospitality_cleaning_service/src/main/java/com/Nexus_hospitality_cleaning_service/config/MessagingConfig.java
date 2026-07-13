package com.Nexus_hospitality_cleaning_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
    public static final String EXCHANGE = "nexus.events";
    public static final String CHECKOUT_QUEUE = "cleaning.checkout-events";
    @Bean TopicExchange nexusExchange() { return new TopicExchange(EXCHANGE, true, false); }
    @Bean Queue cleaningCheckoutQueue() { return QueueBuilder.durable(CHECKOUT_QUEUE).build(); }
    @Bean Binding checkoutBinding(Queue cleaningCheckoutQueue, TopicExchange nexusExchange) {
        return BindingBuilder.bind(cleaningCheckoutQueue).to(nexusExchange).with("stay.checkout.completed");
    }
}
