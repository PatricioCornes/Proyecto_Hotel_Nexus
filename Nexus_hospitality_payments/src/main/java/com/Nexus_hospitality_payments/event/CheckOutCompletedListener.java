package com.Nexus_hospitality_payments.event;

import com.Nexus_hospitality_payments.config.MessagingConfig;
import com.Nexus_hospitality_payments.servicio.FacturacionServicio;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class CheckOutCompletedListener {
    private final FacturacionServicio facturacion;
    private final ObjectMapper mapper;
    public CheckOutCompletedListener(FacturacionServicio facturacion, ObjectMapper mapper) {
        this.facturacion = facturacion;
        this.mapper = mapper;
    }

    @RabbitListener(queues = MessagingConfig.CHECKOUT_QUEUE)
    public void receive(String json) throws Exception {
        StayEvent event = mapper.readValue(json, StayEvent.class);
        facturacion.consolidar(event.reservaCodigo());
    }
    record StayEvent(String eventId, String tipo, String reservaCodigo, Long habitacionId) {}
}
