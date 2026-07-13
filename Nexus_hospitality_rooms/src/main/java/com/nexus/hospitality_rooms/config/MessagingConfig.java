package com.nexus.hospitality_rooms.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
    public static final String EXCHANGE = "nexus.events";
    public static final String STAY_QUEUE = "rooms.stay-events";
    public static final String CLEANING_QUEUE = "rooms.cleaning-events";

    @Bean TopicExchange nexusExchange() { return new TopicExchange(EXCHANGE, true, false); }
    @Bean Queue roomsStayQueue() { return QueueBuilder.durable(STAY_QUEUE).build(); }
    @Bean Queue roomsCleaningQueue() { return QueueBuilder.durable(CLEANING_QUEUE).build(); }
    @Bean Binding checkInBinding(Queue roomsStayQueue, TopicExchange nexusExchange) {
        return BindingBuilder.bind(roomsStayQueue).to(nexusExchange).with("stay.checkin.completed");
    }
    @Bean Binding checkOutBinding(Queue roomsStayQueue, TopicExchange nexusExchange) {
        return BindingBuilder.bind(roomsStayQueue).to(nexusExchange).with("stay.checkout.completed");
    }
    @Bean Binding cleaningBinding(Queue roomsCleaningQueue, TopicExchange nexusExchange) {
        return BindingBuilder.bind(roomsCleaningQueue).to(nexusExchange).with("cleaning.finished");
    }
}
