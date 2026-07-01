package com.Nexus_hospitality_garage.dto;


import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EstacionamientoDTO {


    @NotBlank(message = "El código de la plaza es obligatorio")
    @Size(min = 2, max = 10, message = "El código de la plaza debe tener entre 2 y 10 caracteres")
    private String codigoPlaza;

    @NotBlank(message = "El estado inicial de la plaza es obligatorio")
    private String estado;

    // No es obligatorio (@NotBlank) al guardar, ya que la plaza puede crearse vacía
    @Size(max = 15, message = "La patente no puede superar los 15 caracteres")
    private String patenteVehiculo;

    private String tipoVehiculo;

    // Cambiado a LocalDateTime y validado para que sea la fecha actual o futura al registrar el ingreso
    @FutureOrPresent(message = "La fecha de ingreso no puede ser una fecha pasada")
    private LocalDateTime fechaIngreso;

    private String habitacionHuesped;

    private String nombreContacto;

    private String observaciones;

}
