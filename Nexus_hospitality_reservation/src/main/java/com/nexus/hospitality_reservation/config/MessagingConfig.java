package com.nexus.hospitality_reservation.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
    public static final String EXCHANGE = "nexus.events";
    public static final String PAYMENT_QUEUE = "reservations.payment-events";

    @Bean TopicExchange nexusExchange() { return new TopicExchange(EXCHANGE, true, false); }
    @Bean Queue reservationPaymentQueue() { return QueueBuilder.durable(PAYMENT_QUEUE).build(); }
    @Bean Binding paymentCompletedBinding(Queue reservationPaymentQueue, TopicExchange nexusExchange) {
        return BindingBuilder.bind(reservationPaymentQueue).to(nexusExchange).with("payment.completed");
    }
}
