package com.nexus.hospitality_reservation.event;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import com.nexus.hospitality_reservation.config.MessagingConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class ReservationEventPublisher {
    private final RabbitTemplate rabbit;
    private final ObjectMapper mapper;

    public ReservationEventPublisher(RabbitTemplate rabbit, ObjectMapper mapper) {
        this.rabbit = rabbit;
        this.mapper = mapper;
    }

    public void reservationCreated(String codigo) {
        send("reservation.created", new ReservationCreated(UUID.randomUUID().toString(), codigo));
    }

    public void checkInCompleted(String codigo, Long habitacionId) {
        send("stay.checkin.completed", new StayEvent(UUID.randomUUID().toString(), "CHECK_IN", codigo, habitacionId));
    }

    public void checkOutCompleted(String codigo, Long habitacionId) {
        send("stay.checkout.completed", new StayEvent(UUID.randomUUID().toString(), "CHECK_OUT", codigo, habitacionId));
    }

    private void send(String key, Object event) {
        try {
            String json = mapper.writeValueAsString(event);
            Runnable publish = () -> rabbit.convertAndSend(MessagingConfig.EXCHANGE, key, json);
            if (TransactionSynchronizationManager.isActualTransactionActive()) {
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                    @Override public void afterCommit() { publish.run(); }
                });
            } else publish.run();
        } catch (JacksonException e) {
            throw new IllegalStateException("No fue posible serializar el evento", e);
        }
    }

    record ReservationCreated(String eventId, String reservaCodigo) {}
    record StayEvent(String eventId, String tipo, String reservaCodigo, Long habitacionId) {}
}
