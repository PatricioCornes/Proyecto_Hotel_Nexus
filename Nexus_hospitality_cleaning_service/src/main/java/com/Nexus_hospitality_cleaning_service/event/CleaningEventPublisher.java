package com.Nexus_hospitality_cleaning_service.event;

import com.Nexus_hospitality_cleaning_service.config.MessagingConfig;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class CleaningEventPublisher {
    private final RabbitTemplate rabbit;
    private final ObjectMapper mapper;
    public CleaningEventPublisher(RabbitTemplate rabbit, ObjectMapper mapper) { this.rabbit = rabbit; this.mapper = mapper; }

    public void cleaningFinished(Long limpiezaId, Long habitacionId, Long empleadoId) {
        try {
            var event = new CleaningFinished(UUID.randomUUID().toString(), limpiezaId, habitacionId, empleadoId);
            sendAfterCommit("cleaning.finished", mapper.writeValueAsString(event));
        } catch (JacksonException e) {
            throw new IllegalStateException("No fue posible serializar CleaningFinished", e);
        }
    }
    public void cleaningRequested(Long limpiezaId, String reservaCodigo, Long habitacionId, Long empleadoId) {
        try {
            var event = new CleaningRequested(UUID.randomUUID().toString(), limpiezaId, reservaCodigo, habitacionId, empleadoId);
            sendAfterCommit("cleaning.requested", mapper.writeValueAsString(event));
        } catch (JacksonException e) {
            throw new IllegalStateException("No fue posible serializar CleaningRequested", e);
        }
    }
    private void sendAfterCommit(String key, String json) {
        Runnable publish = () -> rabbit.convertAndSend(MessagingConfig.EXCHANGE, key, json);
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override public void afterCommit() { publish.run(); }
            });
        } else publish.run();
    }
    record CleaningRequested(String eventId, Long limpiezaId, String reservaCodigo, Long habitacionId, Long empleadoId) {}
    record CleaningFinished(String eventId, Long limpiezaId, Long habitacionId, Long empleadoId) {}
}
