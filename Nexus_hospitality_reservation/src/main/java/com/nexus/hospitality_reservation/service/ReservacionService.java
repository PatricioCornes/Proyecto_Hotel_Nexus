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


    public List<Reservacion> findAll() {
        return reservacionRepository.findAll();
    }


    public Reservacion findById(Long id) {
        return reservacionRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservacion no encontrada")); 
    }    



    public Reservacion save(ReservacionDTO dto) {
        try {
            HabitacionDTO habitacion = habitacionClient.obtenerHabitacion(dto.getHabitacionId());
            
            if (habitacion == null || !habitacion.getEstado().equalsIgnoreCase("Disponible")) {
                throw new RuntimeException("¡Error! La habitación número " 
                        + (habitacion != null ? habitacion.getNumero() : dto.getHabitacionId()) 
                        + " no está disponible actualmente.");
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("No se pudo verificar la disponibilidad de la habitación debido a un fallo en el servidor de Rooms.");
        }
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


    public Reservacion update(Long id, ReservacionDTO dto) {
        Reservacion reservacion = findById(id); // Verifica que exista
        
        // Actualiza los campos permitidos
        reservacion.setClienteId(dto.getClienteId());
        reservacion.setHabitacionId(dto.getHabitacionId());
        reservacion.setFechaIngreso(dto.getFechaIngreso());
        reservacion.setFechaSalida(dto.getFechaSalida());
        return reservacionRepository.save(reservacion);
    }


    public void delete(Long id) {
        reservacionRepository.deleteById(id);
    }    




    @Autowired
    private HabitacionClient habitacionClient; // Inyectamos el cliente Feign

    public ReservacionDTO obtenerReservacionConDetalle(Long id) {
        // 1. Buscamos la reservación en la base de datos local de Reservas
        Reservacion reservacion = reservacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservación no encontrada con ID: " + id));

        HabitacionDTO habitacionDTO = null;
        try {
            habitacionDTO = habitacionClient.obtenerHabitacion(reservacion.getHabitacionId());
        } catch (Exception e) {

            System.err.println("No se pudieron cargar los datos de la habitación: " + e.getMessage());
        }

        ReservacionDTO respuestaDTO = new ReservacionDTO();
        respuestaDTO.setClienteId(reservacion.getClienteId());
        respuestaDTO.setFechaIngreso(reservacion.getFechaIngreso());
        respuestaDTO.setFechaSalida(reservacion.getFechaSalida());
        
        respuestaDTO.setHabitacion(habitacionDTO); 

        return respuestaDTO;
    }
}


