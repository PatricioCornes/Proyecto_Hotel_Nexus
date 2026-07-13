package com.Nexus_hospitality_garage.dto;


import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EstacionamientoDTO {


    @NotBlank(message = "El código de la plaza es obligatorio")
    @Size(min = 2, max = 10, message = "El código de la plaza debe tener entre 2 y 10 caracteres")
    private String codigoPlaza;

    @NotBlank(message = "El estado inicial de la plaza es obligatorio")
    @Pattern(regexp = "DISPONIBLE|OCUPADO|RESERVADO|EN_MANTENIMIENTO", message = "Estado de estacionamiento no valido")
    private String estado;

    @Size(max = 15, message = "La patente no puede superar los 15 caracteres")
    @Pattern(regexp = "[A-Za-z0-9-]*", message = "Formato de patente invalido")
    private String patenteVehiculo;

    private String tipoVehiculo;

    @FutureOrPresent(message = "La fecha de ingreso no puede ser una fecha pasada")
    private LocalDateTime fechaIngreso;

    private String habitacionHuesped;

    private String nombreContacto;

    private String observaciones;

}
