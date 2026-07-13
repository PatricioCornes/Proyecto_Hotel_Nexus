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

    @Column(unique = true, length = 10, nullable = false)
    private String codigoPlaza;

    @Column(nullable = false)
    private String estado;

    @Column(length = 15)
    private String patenteVehiculo;

    @Column
    private String tipoVehiculo;

    @Column
    private LocalDateTime fechaIngreso; 

    @Column
    private String habitacionHuesped;

    @Column
    private String nombreContacto;

    @Column 
    private String observaciones; 

}
