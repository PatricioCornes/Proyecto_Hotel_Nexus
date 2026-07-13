package com.Nexus_hospitality_payments.event;

import com.Nexus_hospitality_payments.config.MessagingConfig;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class PaymentEventPublisher {
    private final RabbitTemplate rabbit;
    private final ObjectMapper mapper;
    public PaymentEventPublisher(RabbitTemplate rabbit, ObjectMapper mapper) { this.rabbit = rabbit; this.mapper = mapper; }

    public void paymentCompleted(String pagoReferencia, String reservaCodigo, boolean aprobado) {
        try {
            var event = new PaymentCompleted(UUID.randomUUID().toString(), pagoReferencia, reservaCodigo, aprobado);
            String json = mapper.writeValueAsString(event);
            Runnable publish = () -> rabbit.convertAndSend(MessagingConfig.EXCHANGE, "payment.completed", json);
            if (TransactionSynchronizationManager.isActualTransactionActive()) {
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                    @Override public void afterCommit() { publish.run(); }
                });
            } else publish.run();
        } catch (JacksonException e) {
            throw new IllegalStateException("No fue posible serializar PaymentCompleted", e);
        }
    }
    record PaymentCompleted(String eventId, String pagoReferencia, String reservaCodigo, boolean aprobado) {}
}
