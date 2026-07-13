package com.Nexus_hospitality_payments.entidad;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cargos_adicionales")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CargoAdicional {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "reserva_codigo")
    private String reservaCodigo;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    @Column(nullable = false)
    private LocalDateTime fecha;
}
