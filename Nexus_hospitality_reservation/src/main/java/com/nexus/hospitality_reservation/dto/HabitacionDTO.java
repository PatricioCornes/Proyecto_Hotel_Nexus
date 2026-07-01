package com.nexus.hospitality_reservation.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class HabitacionDTO {
    
    private Long id;
    private String numero;
    private String tipo;
    private BigDecimal precioPorNoche;
    private String estado;
    
}