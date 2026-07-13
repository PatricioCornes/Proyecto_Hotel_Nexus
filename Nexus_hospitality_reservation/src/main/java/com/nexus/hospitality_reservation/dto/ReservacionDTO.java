package com.nexus.hospitality_reservation.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ReservacionDTO {

    private Long id;
    private String codigoReserva;
    private String estado;

    @NotBlank(message = "El id del cliente es obligatorio")
    @Size(max = 80, message = "El id del cliente no puede superar 80 caracteres")
    private String clienteId;

    @Positive(message = "El id de habitacion debe ser positivo")
    private Long habitacionId;
    @Size(max = 30, message = "El tipo de habitacion no puede superar 30 caracteres")
    private String tipoHabitacion;

    @NotNull(message = "La fecha de ingreso es obligatoria.")
    @FutureOrPresent(message = "La fecha de ingreso no puede estar en el pasado.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaIngreso;

    @NotNull(message = "La fecha de salida es obligatoria.")
    @FutureOrPresent(message = "La fecha de salida no puede estar en el pasado.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaSalida;

    private HabitacionDTO habitacion;

    @jakarta.validation.constraints.AssertTrue(message = "La fecha de salida debe ser posterior a la fecha de ingreso")
    public boolean isRangoFechasValido() {
        return fechaIngreso == null || fechaSalida == null || fechaSalida.isAfter(fechaIngreso);
    }

}
