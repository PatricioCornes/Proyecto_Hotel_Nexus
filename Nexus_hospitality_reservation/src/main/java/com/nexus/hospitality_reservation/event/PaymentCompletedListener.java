package com.nexus.hospitality_reservation.event;

import tools.jackson.databind.ObjectMapper;
import com.nexus.hospitality_reservation.config.MessagingConfig;
import com.nexus.hospitality_reservation.service.ReservacionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentCompletedListener {
    private final ReservacionService service;
    private final ObjectMapper mapper;

    public PaymentCompletedListener(ReservacionService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @RabbitListener(queues = MessagingConfig.PAYMENT_QUEUE)
    public void receive(String json) throws Exception {
        PaymentCompleted event = mapper.readValue(json, PaymentCompleted.class);
        if (event.aprobado()) service.confirmarPorPago(event.reservaCodigo());
    }

    public record PaymentCompleted(String eventId, String pagoReferencia, String reservaCodigo, boolean aprobado) {}
}
