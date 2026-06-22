package com.nexus.hospitality_reservation.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * DTO (Data Transfer Object) para transferir datos de reservaciones
 * Contiene validaciones para asegurar que los datos sean correctos
 */
@Data
public class ReservacionDTO {

    // ID del cliente que realiza la reservación
    @NotBlank(message = "El id del cliente es obligatorio")
    private String clienteId;

    // ID de la habitación a reservar limites sujetos a cambios
    @NotNull(message = "El ID de la habitacion es obligatorio")
    @Min(value = 1, message = "El número de habitación debe ser como mínimo 1")
    @Max(value = 100, message = "El número de habitación no puede ser mayor a 100")
    private Integer habitacionId;

    // Fecha de entrada/ingreso a la habitación en formato yyyy-MM-dd
    @NotNull(message = "La fecha de ingreso es obligatoria.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaIngreso;

    // Fecha de salida/checkout de la habitación, debe ser una fecha futura en formato yyyy-MM-dd
    @NotNull(message = "La fecha de salida es obligatoria.")
    @Future(message = "La fecha de salida debe ser una fecha futura.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaSalida;

}
