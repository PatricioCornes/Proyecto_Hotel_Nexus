package com.Nexus_hospitality_restaurant.controlador;

import com.Nexus_hospitality_restaurant.entidad.Consumo;
import com.Nexus_hospitality_restaurant.repositorio.ConsumoRepositorio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/consumos")
public class ConsumoControlador {
    private final ConsumoRepositorio repository;
    public ConsumoControlador(ConsumoRepositorio repository) { this.repository = repository; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Consumo registrar(@Valid @RequestBody ConsumoRequest request) {
        return repository.save(Consumo.builder()
                .reservaCodigo(request.reservaCodigo()).descripcion(request.descripcion())
                .monto(request.monto()).fecha(LocalDateTime.now()).build());
    }

    @GetMapping("/reserva/{codigo}")
    public List<Consumo> listar(@PathVariable String codigo) { return repository.findByReservaCodigo(codigo); }

    @GetMapping("/reserva/{codigo}/total")
    public BigDecimal total(@PathVariable String codigo) {
        return repository.findByReservaCodigo(codigo).stream().map(Consumo::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public record ConsumoRequest(@NotBlank @Size(max = 80) String reservaCodigo,
                                 @NotBlank @Size(max = 250) String descripcion,
                                 @NotNull @Positive @Digits(integer = 10, fraction = 2) BigDecimal monto) {}
}
