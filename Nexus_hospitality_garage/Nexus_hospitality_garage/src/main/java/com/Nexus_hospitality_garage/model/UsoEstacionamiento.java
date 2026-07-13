package com.Nexus_hospitality_garage.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "usos_estacionamiento")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UsoEstacionamiento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "reserva_codigo")
    private String reservaCodigo;
    @Column(nullable = false)
    private String patente;
    @Column(nullable = false)
    private LocalDateTime entrada;
    private LocalDateTime salida;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifaHora;
    @Column(precision = 10, scale = 2)
    private BigDecimal costo;
}
