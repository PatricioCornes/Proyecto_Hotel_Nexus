package com.Nexus_hospitality_payments.entidad;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "facturas_finales")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FacturaFinal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, name = "reserva_codigo")
    private String reservaCodigo;
    private BigDecimal habitacion;
    private BigDecimal restaurante;
    private BigDecimal estacionamiento;
    private BigDecimal serviciosAdicionales;
    private BigDecimal total;
    private BigDecimal pagado;
    private BigDecimal saldo;
    private LocalDateTime generadaEn;
}
