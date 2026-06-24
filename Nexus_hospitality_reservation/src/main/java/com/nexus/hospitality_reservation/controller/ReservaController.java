package com.nexus.hospitality_reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


import com.nexus.hospitality_reservation.service.ReservacionService;

import jakarta.validation.Valid;

import com.nexus.hospitality_reservation.dto.*;
import com.nexus.hospitality_reservation.model.Reservacion;

import java.util.List;

/**
 * Controlador REST que expone los endpoints de la API para gestionar reservaciones
 * Base path: /api/v1/reservaciones
 * Maneja las peticiones HTTP (GET, POST, PUT, DELETE)
 */
@RestController
@RequestMapping("/api/v1/reservaciones")
public class ReservaController {

    // Servicio inyectado que contiene la lógica de negocio
    @Autowired
    private ReservacionService reservacionService;

    /**
     * GET /api/v1/reservaciones
     * Obtiene la lista de todas las reservaciones
     * @return 200 OK con la lista, o 204 No Content si está vacía
     */
    @GetMapping
    public ResponseEntity<List<Reservacion>> listar() {
        List<Reservacion> reservacion = reservacionService.findAll();
        if (reservacion.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(reservacion); // 200 OK
    }

    /**
     * GET /api/v1/reservaciones/{id}
     * Busca una reservación específica por su ID
     * @param id ID de la reservación a buscar
     * @return 200 OK con la reservación, o 404 Not Found si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reservacion> buscar(@PathVariable Long id) {
        try {
            Reservacion reservacion = reservacionService.findById(id);
            return ResponseEntity.ok(reservacion); // 200 OK
        } catch (Exception c) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    /**
     * POST /api/v1/reservaciones
     * Crea una nueva reservación validando los datos del DTO
     * @param dto Datos de la nueva reservación
     * @return 201 Created con la reservación creada
     */
    @PostMapping
    public ResponseEntity<Reservacion> guardar(@Valid @RequestBody ReservacionDTO dto) {
        Reservacion reservacionNueva = reservacionService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservacionNueva); // 201 Created
    }

    /**
     * PUT /api/v1/reservaciones/{id}
     * Actualiza una reservación existente
     * @param id ID de la reservación a actualizar
     * @param dto Nuevos datos de la reservación
     * @return 200 OK con la reservación actualizada, o 404 Not Found si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<Reservacion> actualizar(@PathVariable Long id, @Valid @RequestBody ReservacionDTO dto) {
        try {
            Reservacion reservacionActual = reservacionService.update(id, dto);
            return ResponseEntity.ok(reservacionActual); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    /**
     * DELETE /api/v1/reservaciones/{id}
     * Elimina una reservación por su ID
     * @param id ID de la reservación a eliminar
     * @return 204 No Content si se eliminó correctamente, o 404 Not Found si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            reservacionService.delete(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    /**
     * POST /api/v1/reservaciones/confirmar
     * Confirma una reservación por su código.
     * Se usa desde Payments al aprobar un pago.
     */
    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarPorCodigo(@RequestParam String codigoReserva) {
        try {
            Reservacion reservacion = reservacionService.confirmarPorCodigoReserva(codigoReserva);
            return ResponseEntity.ok(reservacion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/v1/reservaciones/checkout?codigoReserva=...
     * Orquesta el check-out: habitación SUCIA (Rooms) + tarea limpieza (Cleaning).
     */
    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestParam String codigoReserva) {
        try {
            // En este MVP usamos el orquestador dentro de ReservacionService
            Reservacion reservacion = reservacionService.checkoutPorCodigoReserva(codigoReserva);
            return ResponseEntity.ok(reservacion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}


