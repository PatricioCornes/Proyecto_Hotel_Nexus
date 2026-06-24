package com.nexus.hospitality_reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad JPA que representa una reservación de habitación en la base de datos
 * Mapea a la tabla 'reservacion'
 */
@Entity
@Table(name = "reservacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservacion {

    // ID único generado automáticamente por la base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Código único de la reservación (ej: RES-A1B2C3D4), máximo 12 caracteres
    @Column(unique = true, length = 12, nullable = false)
    private String codigoReserva;

    // ID del cliente que hace la reservación
    @Column(nullable = false)
    private String clienteId;

    // ID de la habitación reservada
    @Column(nullable = false)
    private String habitacionId;

    // Fecha de entrada/check-in
    @Column(nullable = false)
    private LocalDate fechaIngreso;

    // Fecha de salida/check-out
    @Column(nullable = false)
    private LocalDate fechaSalida;

    // Estado actual de la reservación (ej: PENDIENTE, CONFIRMADA, CANCELADA)
    @Column(nullable = false)
    private String estado;

    // Fecha y hora cuando se creó la reservación
    @Column(nullable = false)
    private LocalDateTime fechaEmicion;

    // transaccionPagoId;

}
