package com.nexus.hospitality_reservation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexus.hospitality_reservation.repository.ReservacionRepository;
import com.nexus.hospitality_reservation.dto.ReservacionDTO;
import com.nexus.hospitality_reservation.model.*;

import jakarta.transaction.Transactional;


/**
 * Servicio que maneja la lógica de negocio para operaciones de reservaciones
 * Contiene métodos para crear, leer, actualizar y eliminar reservaciones (CRUD)
 */
@Transactional
@Service
public class ReservacionService {

    // Repositorio inyectado para acceder a los datos de la base de datos
    @Autowired
    private ReservacionRepository reservacionRepository;

    @Autowired
    private IntegracionHabitacionService integracionHabitacionService;

    @Autowired
    private CheckoutOrchestratorService checkoutOrchestratorService;



    /**
     * Obtiene todas las reservaciones de la base de datos
     * @return Lista de todas las reservaciones
     */
    public List<Reservacion> findAll() {
        return reservacionRepository.findAll();
    }

    /**
     * Busca una reservación por su ID
     * @param id ID de la reservación
     * @return Reservación encontrada
     * @throws RuntimeException si la reservación no existe
     */
    public Reservacion findById(Long id) {
        return reservacionRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservacion no encontrada")); 
    }    


    /**
     * Crea una nueva reservación a partir de un DTO
     * Genera un código único, asigna fecha de emisión actual y estado inicial PENDIENTE
     * @param dto Datos de la reservación desde el cliente
     * @return Reservación creada y guardada en BD
     */
    public Reservacion save(ReservacionDTO dto) {
        Reservacion reservacion = new Reservacion();
        reservacion.setClienteId(dto.getClienteId());
        reservacion.setHabitacionId(dto.getHabitacionId());
        reservacion.setFechaIngreso(dto.getFechaIngreso());
        reservacion.setFechaSalida(dto.getFechaSalida());
        reservacion.setFechaEmicion(LocalDateTime.now()); // Fecha y hora actual
        reservacion.setEstado("PENDIENTE"); // Estado inicial
       
        String codigoCorto = java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        reservacion.setCodigoReserva("RES-" + codigoCorto);

        return reservacionRepository.save(reservacion);
    }

    /**
     * Actualiza una reservación existente
     * @param id ID de la reservación a actualizar
     * @param dto Nuevos datos de la reservación
     * @return Reservación actualizada
     * @throws RuntimeException si la reservación no existe
     */
    public Reservacion update(Long id, ReservacionDTO dto) {
        Reservacion reservacion = findById(id); // Verifica que exista
        
        
        reservacion.setClienteId(dto.getClienteId());
        reservacion.setHabitacionId(dto.getHabitacionId());
        reservacion.setFechaIngreso(dto.getFechaIngreso());
        reservacion.setFechaSalida(dto.getFechaSalida());
        return reservacionRepository.save(reservacion);
    }

    /**
     * 
     * @param id 
     */
    public void delete(Long id) {
        reservacionRepository.deleteById(id);
    }


    public Reservacion confirmarPorCodigoReserva(String codigoReserva) {
        Reservacion reservacion = reservacionRepository.findAll()
                .stream()
                .filter(r -> r.getCodigoReserva() != null && r.getCodigoReserva().equals(codigoReserva))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Reservacion no encontrada con codigoReserva=" + codigoReserva));

        // 1) Confirmar reserva
        reservacion.setEstado("reservacìon confirmada exitosamente");

        // 2) Asignar habitación disponible en Rooms
        try {
            // MVP: rooms devuelve habitacionId/estado, pero Reservacion actual no lo persiste aún
            integracionHabitacionService.asignarHabitacionPorCodigoReserva(codigoReserva);
        } catch (Exception e) {
            throw new IllegalStateException("No fue posible asignar habitación para codigoReserva=" + codigoReserva, e);
        }

        return reservacionRepository.save(reservacion);
    }


    /**
     * Orquesta check-out: Rooms marca SUCIA + Cleaning crea tarea.
     */
    public Reservacion checkoutPorCodigoReserva(String codigoReserva) {
        return checkoutOrchestratorService.checkoutPorCodigoReserva(codigoReserva);
    }

}



