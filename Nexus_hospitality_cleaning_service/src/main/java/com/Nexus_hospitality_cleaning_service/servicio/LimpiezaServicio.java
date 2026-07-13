package com.Nexus_hospitality_cleaning_service.servicio;

import com.Nexus_hospitality_cleaning_service.controlador.LimpiezaControlador;
import com.Nexus_hospitality_cleaning_service.entidad.Limpieza;
import com.Nexus_hospitality_cleaning_service.event.CleaningEventPublisher;
import com.Nexus_hospitality_cleaning_service.repositorio.LimpiezaRepositorio;
import com.Nexus_hospitality_cleaning_service.config.ServiceUrlResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import java.time.LocalDate;
import java.util.*;

@Service
public class LimpiezaServicio {
    private final LimpiezaRepositorio repository;
    private final RestClient staff;
    private final ServiceUrlResolver resolver;
    private final CleaningEventPublisher events;

    public LimpiezaServicio(LimpiezaRepositorio repository, RestClient.Builder builder,
                            ServiceUrlResolver resolver,
                            CleaningEventPublisher events) {
        this.repository = repository;
        this.staff = builder.build();
        this.resolver = resolver;
        this.events = events;
    }

    @Transactional(readOnly = true)
    public List<Limpieza> listar() { return repository.findAll(); }

    @Transactional(readOnly = true)
    public Limpieza obtenerPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Limpieza no encontrada con id=" + id));
    }

    @Transactional
    public Limpieza crear(LimpiezaControlador.LimpiezaRequest request) {
        validarReferencia(request.referencia(), null);
        return repository.save(Limpieza.builder().referencia(request.referencia())
                .habitacionId(request.habitacionId()).fecha(request.fecha()).tipo(request.tipo())
                .estado(request.estado()).build());
    }

    @Transactional
    public Limpieza crearAutomatica(String reservaCodigo, Long habitacionId) {
        Optional<Limpieza> existente = repository.findByReservaCodigo(reservaCodigo);
        if (existente.isPresent()) return existente.get();
        @SuppressWarnings("unchecked")
        Map<String, Object> empleado = staff.post().uri(resolver.resolve("nexus-staff") + "/empleados/interno/asignar/LIMPIEZA")
                .retrieve().body(Map.class);
        if (empleado == null || !(empleado.get("id") instanceof Number id)) {
            throw new IllegalStateException("Staff no devolvió un empleado válido");
        }
        Limpieza limpieza = Limpieza.builder()
                .referencia("LIMP-" + reservaCodigo).reservaCodigo(reservaCodigo)
                .habitacionId(habitacionId).empleadoId(id.longValue()).fecha(LocalDate.now())
                .tipo("CHECK_OUT").estado("ASIGNADA").build();
        Limpieza guardada = repository.save(limpieza);
        events.cleaningRequested(guardada.getId(), reservaCodigo, habitacionId, guardada.getEmpleadoId());
        return guardada;
    }

    @Transactional
    public Limpieza finalizar(Long id) {
        Limpieza limpieza = obtenerPorId(id);
        if ("FINALIZADA".equals(limpieza.getEstado())) return limpieza;
        limpieza.setEstado("FINALIZADA");
        repository.save(limpieza);
        if (limpieza.getEmpleadoId() != null) {
            staff.post().uri(resolver.resolve("nexus-staff") + "/empleados/interno/{id}/liberar", limpieza.getEmpleadoId()).retrieve().toBodilessEntity();
        }
        events.cleaningFinished(limpieza.getId(), limpieza.getHabitacionId(), limpieza.getEmpleadoId());
        return limpieza;
    }

    @Transactional
    public Limpieza actualizar(Long id, LimpiezaControlador.LimpiezaRequest request) {
        Limpieza limpieza = obtenerPorId(id);
        validarReferencia(request.referencia(), limpieza.getId());
        limpieza.setReferencia(request.referencia());
        limpieza.setHabitacionId(request.habitacionId());
        limpieza.setFecha(request.fecha());
        limpieza.setTipo(request.tipo());
        limpieza.setEstado(request.estado());
        return repository.save(limpieza);
    }

    @Transactional
    public void eliminar(Long id) { repository.delete(obtenerPorId(id)); }

    private void validarReferencia(String referencia, Long idActual) {
        repository.findByReferencia(referencia).ifPresent(l -> {
            if (idActual == null || !l.getId().equals(idActual))
                throw new IllegalStateException("Ya existe una limpieza con referencia=" + referencia);
        });
    }
}
