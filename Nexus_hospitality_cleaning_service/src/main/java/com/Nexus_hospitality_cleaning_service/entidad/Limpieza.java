package com.Nexus_hospitality_cleaning_service.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "limpiezas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Limpieza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String referencia;

    @Column(name = "habitacion_id", nullable = false)
    private Long habitacionId;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String estado;

    @Column(name = "reserva_codigo")
    private String reservaCodigo;

    @Column(name = "empleado_id")
    private Long empleadoId;
}

