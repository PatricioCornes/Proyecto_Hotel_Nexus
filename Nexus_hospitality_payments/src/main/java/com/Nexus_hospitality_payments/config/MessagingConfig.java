package com.Nexus_hospitality_payments.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
    public static final String EXCHANGE = "nexus.events";
    public static final String CHECKOUT_QUEUE = "payments.checkout-events";
    @Bean TopicExchange nexusExchange() { return new TopicExchange(EXCHANGE, true, false); }
    @Bean Queue paymentsCheckoutQueue() { return QueueBuilder.durable(CHECKOUT_QUEUE).build(); }
    @Bean Binding checkoutBinding(Queue paymentsCheckoutQueue, TopicExchange nexusExchange) {
        return BindingBuilder.bind(paymentsCheckoutQueue).to(nexusExchange).with("stay.checkout.completed");
    }
}
