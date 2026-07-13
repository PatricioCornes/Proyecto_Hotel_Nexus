package com.Nexus_hospitality_payments.controlador;

import com.Nexus_hospitality_payments.entidad.CargoAdicional;
import com.Nexus_hospitality_payments.repositorio.CargoAdicionalRepositorio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cargos-adicionales")
public class CargoAdicionalControlador {
    private final CargoAdicionalRepositorio repository;
    public CargoAdicionalControlador(CargoAdicionalRepositorio repository) { this.repository = repository; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CargoAdicional crear(@Valid @RequestBody CargoRequest request) {
        return repository.save(CargoAdicional.builder().reservaCodigo(request.reservaCodigo())
                .descripcion(request.descripcion()).monto(request.monto()).fecha(LocalDateTime.now()).build());
    }

    @GetMapping("/reserva/{codigo}")
    public List<CargoAdicional> listar(@PathVariable String codigo) {
        return repository.findByReservaCodigo(codigo);
    }

    public record CargoRequest(@NotBlank String reservaCodigo, @NotBlank String descripcion,
                               @NotNull @Positive BigDecimal monto) {}
}
