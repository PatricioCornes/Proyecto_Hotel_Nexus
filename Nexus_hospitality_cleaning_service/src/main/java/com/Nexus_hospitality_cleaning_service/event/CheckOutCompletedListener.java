package com.Nexus_hospitality_cleaning_service.event;

import com.Nexus_hospitality_cleaning_service.config.MessagingConfig;
import com.Nexus_hospitality_cleaning_service.servicio.LimpiezaServicio;
import tools.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CheckOutCompletedListener {
    private final LimpiezaServicio servicio;
    private final ObjectMapper mapper;
    public CheckOutCompletedListener(LimpiezaServicio servicio, ObjectMapper mapper) { this.servicio = servicio; this.mapper = mapper; }

    @RabbitListener(queues = MessagingConfig.CHECKOUT_QUEUE)
    public void receive(String json) throws Exception {
        StayEvent event = mapper.readValue(json, StayEvent.class);
        servicio.crearAutomatica(event.reservaCodigo(), event.habitacionId());
    }
    record StayEvent(String eventId, String tipo, String reservaCodigo, Long habitacionId) {}
}
