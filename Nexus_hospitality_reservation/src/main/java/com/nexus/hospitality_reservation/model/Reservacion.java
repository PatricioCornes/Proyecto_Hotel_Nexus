package com.nexus.hospitality_reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "reservacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 12, nullable = false)
    private String codigoReserva;

    @Column(nullable = false)
    private String clienteId;

    @Column
    private Long habitacionId;

    private String tipoHabitacion;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioPorNoche;

    @Column(nullable = false)
    private LocalDate fechaIngreso;

    @Column(nullable = false)
    private LocalDate fechaSalida;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private LocalDateTime fechaEmicion;

}
