package com.Nexus_hospitality_garage.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estacionamiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estacionamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identificador del espacio (ej. "A-12"). Equivale al 'run' original.
    @Column(unique = true, length = 10, nullable = false)
    private String codigoPlaza;

    // Estado de la plaza (ej. "DISPONIBLE", "OCUPADA", "MANTENCION")
    @Column(nullable = false)
    private String estado;

    // Patente o matrícula del vehículo estacionado
    @Column(length = 15)
    private String patenteVehiculo;

    // Tipo de vehículo (ej. "SEDAN", "SUV", "MOTO")
    @Column
    private String tipoVehiculo;

    // Fecha y hora en la que ingresó el vehículo (Cambio de LocalDate a LocalDateTime)
    @Column
    private LocalDateTime fechaIngreso; 

    // Habitación del huésped al que pertenece el vehículo
    @Column
    private String habitacionHuesped;

    // Nombre de quien registra el auto o del huésped
    @Column
    private String nombreContacto;

    // Algún detalle extra (ej. "Llaves en recepción", "Vehículo con daños previos")
    @Column 
    private String observaciones; 

}
