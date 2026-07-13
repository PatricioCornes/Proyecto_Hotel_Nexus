package com.Nexus_hospitality_garage.controller;

import com.Nexus_hospitality_garage.model.UsoEstacionamiento;
import com.Nexus_hospitality_garage.repository.UsoEstacionamientoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.List;

@RestController
@RequestMapping("/usos-estacionamiento")
public class UsoEstacionamientoController {
    private final UsoEstacionamientoRepository repository;
    public UsoEstacionamientoController(UsoEstacionamientoRepository repository) { this.repository = repository; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsoEstacionamiento registrar(@Valid @RequestBody UsoRequest request) {
        return repository.save(UsoEstacionamiento.builder().reservaCodigo(request.reservaCodigo())
                .patente(request.patente()).entrada(LocalDateTime.now()).tarifaHora(request.tarifaHora()).build());
    }

    @PostMapping("/{id}/salida")
    public UsoEstacionamiento finalizar(@PathVariable Long id) {
        UsoEstacionamiento uso = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Uso de estacionamiento no encontrado"));
        if (uso.getSalida() == null) {
            uso.setSalida(LocalDateTime.now());
            uso.setCosto(calcular(uso, uso.getSalida()));
            repository.save(uso);
        }
        return uso;
    }

    @GetMapping("/reserva/{codigo}")
    public List<UsoEstacionamiento> listar(@PathVariable String codigo) {
        return repository.findByReservaCodigo(codigo);
    }

    @GetMapping("/reserva/{codigo}/total")
    public BigDecimal total(@PathVariable String codigo) {
        return repository.findByReservaCodigo(codigo).stream()
                .map(u -> u.getCosto() != null ? u.getCosto() : calcular(u, LocalDateTime.now()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calcular(UsoEstacionamiento uso, LocalDateTime fin) {
        long minutos = Math.max(1, Duration.between(uso.getEntrada(), fin).toMinutes());
        BigDecimal horas = BigDecimal.valueOf(minutos).divide(BigDecimal.valueOf(60), 0, RoundingMode.CEILING);
        return uso.getTarifaHora().multiply(horas.max(BigDecimal.ONE));
    }

    public record UsoRequest(@NotBlank @Size(max = 80) String reservaCodigo,
                             @NotBlank @Size(max = 15) @Pattern(regexp = "[A-Za-z0-9-]+", message = "Formato de patente invalido") String patente,
                             @NotNull @Positive @Digits(integer = 8, fraction = 2) BigDecimal tarifaHora) {}
}
