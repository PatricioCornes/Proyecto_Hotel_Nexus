package com.nexus.hospitality_reservation.service;

import com.nexus.hospitality_reservation.client.HabitacionClient;
import com.nexus.hospitality_reservation.client.PagoClient;
import com.nexus.hospitality_reservation.dto.HabitacionDTO;
import com.nexus.hospitality_reservation.dto.ReservacionDTO;
import com.nexus.hospitality_reservation.event.ReservationEventPublisher;
import com.nexus.hospitality_reservation.model.Reservacion;
import com.nexus.hospitality_reservation.repository.ReservacionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class ReservacionService {
    private final ReservacionRepository repository;
    private final HabitacionClient habitaciones;
    private final PagoClient pagos;
    private final ReservationEventPublisher events;

    public ReservacionService(ReservacionRepository repository, HabitacionClient habitaciones,
                              PagoClient pagos, ReservationEventPublisher events) {
        this.repository = repository;
        this.habitaciones = habitaciones;
        this.pagos = pagos;
        this.events = events;
    }

    public List<Reservacion> findAll() { return repository.findAll(); }

    public Reservacion findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Reservación no encontrada"));
    }

    public Reservacion findByCodigo(String codigo) {
        return repository.findByCodigoReserva(codigo)
                .orElseThrow(() -> new IllegalArgumentException("Reservación no encontrada: " + codigo));
    }

    public Reservacion save(ReservacionDTO dto) {
        Reservacion reserva = new Reservacion();
        reserva.setCodigoReserva("RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        reserva.setClienteId(dto.getClienteId());
        reserva.setTipoHabitacion(dto.getTipoHabitacion());
        reserva.setFechaIngreso(dto.getFechaIngreso());
        reserva.setFechaSalida(dto.getFechaSalida());
        reserva.setFechaEmicion(LocalDateTime.now());
        reserva.setEstado("PENDIENTE_PAGO");
        Reservacion guardada = repository.save(reserva);
        events.reservationCreated(guardada.getCodigoReserva());
        return guardada;
    }

    public Reservacion confirmarPorPago(String codigo) {
        Reservacion reserva = findByCodigo(codigo);
        if ("CONFIRMADA".equals(reserva.getEstado()) || "OCUPADA".equals(reserva.getEstado())) return reserva;
        if (!"PENDIENTE_PAGO".equals(reserva.getEstado())) {
            throw new IllegalStateException("La reserva no puede confirmarse desde el estado " + reserva.getEstado());
        }
        HabitacionDTO habitacion = habitaciones.asignarHabitacion(
                new HabitacionClient.AsignacionRequest(reserva.getTipoHabitacion()));
        reserva.setHabitacionId(habitacion.getId());
        reserva.setPrecioPorNoche(habitacion.getPrecioPorNoche());
        reserva.setEstado("CONFIRMADA");
        return repository.save(reserva);
    }

    public Reservacion checkIn(Long id) {
        Reservacion reserva = findById(id);
        if (!"CONFIRMADA".equals(reserva.getEstado())) throw new IllegalStateException("La reserva no está confirmada");
        reserva.setEstado("OCUPADA");
        repository.save(reserva);
        events.checkInCompleted(reserva.getCodigoReserva(), reserva.getHabitacionId());
        return reserva;
    }

    public Reservacion checkOut(Long id) {
        Reservacion reserva = findById(id);
        if (!"OCUPADA".equals(reserva.getEstado())) throw new IllegalStateException("La reserva no está en estadía");
        reserva.setEstado("FINALIZADA");
        repository.save(reserva);
        events.checkOutCompleted(reserva.getCodigoReserva(), reserva.getHabitacionId());
        return reserva;
    }

    public Reservacion cancelar(Long id) {
        Reservacion reserva = findById(id);
        if ("FINALIZADA".equals(reserva.getEstado()) || "CANCELADA".equals(reserva.getEstado())) {
            throw new IllegalStateException("La reserva no se puede cancelar");
        }
        long dias = ChronoUnit.DAYS.between(LocalDate.now(), reserva.getFechaIngreso());
        int porcentaje = dias >= 7 ? 100 : dias >= 2 ? 50 : 0;
        pagos.reembolsar(reserva.getCodigoReserva(), new PagoClient.ReembolsoRequest(porcentaje, "Cancelación de reserva"));
        if (reserva.getHabitacionId() != null) {
            habitaciones.cambiarEstado(reserva.getHabitacionId(), new HabitacionClient.EstadoRequest("Disponible"));
        }
        reserva.setEstado("CANCELADA");
        return repository.save(reserva);
    }

    public BigDecimal cargoHabitacion(String codigo) {
        Reservacion reserva = findByCodigo(codigo);
        if (reserva.getPrecioPorNoche() == null) return BigDecimal.ZERO;
        long noches = ChronoUnit.DAYS.between(reserva.getFechaIngreso(), reserva.getFechaSalida());
        return reserva.getPrecioPorNoche().multiply(BigDecimal.valueOf(Math.max(1, noches)));
    }

    public Reservacion update(Long id, ReservacionDTO dto) {
        Reservacion reserva = findById(id);
        if (!"PENDIENTE_PAGO".equals(reserva.getEstado())) {
            throw new IllegalStateException("Sólo se puede editar una reserva pendiente de pago");
        }
        reserva.setClienteId(dto.getClienteId());
        reserva.setTipoHabitacion(dto.getTipoHabitacion());
        reserva.setFechaIngreso(dto.getFechaIngreso());
        reserva.setFechaSalida(dto.getFechaSalida());
        return repository.save(reserva);
    }

    public void delete(Long id) { repository.delete(findById(id)); }

    public ReservacionDTO obtenerReservacionConDetalle(Long id) {
        Reservacion reserva = findById(id);
        ReservacionDTO dto = new ReservacionDTO();
        dto.setId(reserva.getId());
        dto.setCodigoReserva(reserva.getCodigoReserva());
        dto.setEstado(reserva.getEstado());
        dto.setClienteId(reserva.getClienteId());
        dto.setHabitacionId(reserva.getHabitacionId());
        dto.setTipoHabitacion(reserva.getTipoHabitacion());
        dto.setFechaIngreso(reserva.getFechaIngreso());
        dto.setFechaSalida(reserva.getFechaSalida());
        if (reserva.getHabitacionId() != null) {
            try { dto.setHabitacion(habitaciones.obtenerHabitacion(reserva.getHabitacionId())); }
            catch (RuntimeException ignored) { /* El detalle remoto no impide consultar la reserva local. */ }
        }
        return dto;
    }
}
