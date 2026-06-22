package com.nexus.hospitality_reservation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexus.hospitality_reservation.repository.ReservacionRepository;
import com.nexus.hospitality_reservation.dto.ReservacionDTO;
import com.nexus.hospitality_reservation.model.*;
import com.nexus.hospitality_reservation.client.HabitacionClient;
import com.nexus.hospitality_reservation.dto.HabitacionDTO;

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
        // Genera código único: RES-XXXXXXXX (8 caracteres aleatorios)
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
        
        // Actualiza los campos permitidos
        reservacion.setClienteId(dto.getClienteId());
        reservacion.setHabitacionId(dto.getHabitacionId());
        reservacion.setFechaIngreso(dto.getFechaIngreso());
        reservacion.setFechaSalida(dto.getFechaSalida());
        return reservacionRepository.save(reservacion);
    }

    /**
     * Elimina una reservación por su ID
     * @param id ID de la reservación a eliminar
     */
    public void delete(Long id) {
        reservacionRepository.deleteById(id);
    }    



    @Autowired
    private HabitacionClient habitacionClient;

    public Reservacion crearReserva(ReservacionDTO dto) {
        // 1. Llamar al microservicio de habitaciones
        HabitacionDTO habitacion = habitacionClient.obtenerHabitacion(dto.getHabitacionId());
        if (habitacion == null) {
            throw new RuntimeException("Habitación no encontrada");
        }

        // 2. Validar reglas de negocio (ej. si existe o está disponible)
        // Aquí asumimos que la habitación está disponible

        // 3. Crear y guardar la reservación
        Reservacion reservacion = new Reservacion();
        reservacion.setClienteId(dto.getClienteId());
        reservacion.setHabitacionId(dto.getHabitacionId());
        reservacion.setFechaIngreso(dto.getFechaIngreso());
        reservacion.setFechaSalida(dto.getFechaSalida());
        reservacion.setFechaEmicion(LocalDateTime.now());
        reservacion.setEstado("PENDIENTE");
        String codigoCorto = java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        reservacion.setCodigoReserva("RES-" + codigoCorto);

        return reservacionRepository.save(reservacion);
    }
}


