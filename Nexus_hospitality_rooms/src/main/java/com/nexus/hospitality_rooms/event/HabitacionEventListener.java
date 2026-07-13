package com.nexus.hospitality_rooms.event;

import tools.jackson.databind.ObjectMapper;
import com.nexus.hospitality_rooms.config.MessagingConfig;
import com.nexus.hospitality_rooms.servicio.HabitacionServicio;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HabitacionEventListener {
    private final HabitacionServicio servicio;
    private final ObjectMapper mapper;

    public HabitacionEventListener(HabitacionServicio servicio, ObjectMapper mapper) {
        this.servicio = servicio;
        this.mapper = mapper;
    }

    @RabbitListener(queues = MessagingConfig.STAY_QUEUE)
    public void procesarEstadia(String json) throws Exception {
        StayEvent event = mapper.readValue(json, StayEvent.class);
        servicio.cambiarEstado(event.habitacionId(),
                "CHECK_IN".equals(event.tipo()) ? "Ocupada" : "Sucia");
    }

    @RabbitListener(queues = MessagingConfig.CLEANING_QUEUE)
    public void procesarLimpieza(String json) throws Exception {
        CleaningFinished event = mapper.readValue(json, CleaningFinished.class);
        servicio.cambiarEstado(event.habitacionId(), "Disponible");
    }

    public record StayEvent(String eventId, String tipo, String reservaCodigo, Long habitacionId) {}
    public record CleaningFinished(String eventId, Long limpiezaId, Long habitacionId, Long empleadoId) {}
}
