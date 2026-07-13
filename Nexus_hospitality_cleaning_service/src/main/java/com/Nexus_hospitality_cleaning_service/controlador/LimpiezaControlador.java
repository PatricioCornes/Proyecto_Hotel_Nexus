package com.Nexus_hospitality_cleaning_service.controlador;

import com.Nexus_hospitality_cleaning_service.entidad.Limpieza;
import com.Nexus_hospitality_cleaning_service.servicio.LimpiezaServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/limpiezas")
public class LimpiezaControlador {

    private final LimpiezaServicio limpiezaServicio;

    public LimpiezaControlador(LimpiezaServicio limpiezaServicio) {
        this.limpiezaServicio = limpiezaServicio;
    }

    @GetMapping
    public List<Limpieza> listar() {
        return limpiezaServicio.listar();
    }

    @GetMapping("/{id}")
    public Limpieza obtenerPorId(@PathVariable Long id) {
        return limpiezaServicio.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Limpieza crear(@Valid @RequestBody LimpiezaRequest request) {
        return limpiezaServicio.crear(request);
    }

    @PutMapping("/{id}")
    public Limpieza actualizar(@PathVariable Long id, @Valid @RequestBody LimpiezaRequest request) {
        return limpiezaServicio.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        limpiezaServicio.eliminar(id);
    }

    @PostMapping("/{id}/finalizar")
    public Limpieza finalizar(@PathVariable Long id) {
        return limpiezaServicio.finalizar(id);
    }

    public record LimpiezaRequest(
            @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Size(max = 80) String referencia,
            @jakarta.validation.constraints.NotNull @jakarta.validation.constraints.Positive Long habitacionId,
            @jakarta.validation.constraints.NotNull @jakarta.validation.constraints.PastOrPresent java.time.LocalDate fecha,
            @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Size(max = 40) String tipo,
            @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Pattern(regexp = "PENDIENTE|ASIGNADA|EN_PROGRESO|FINALIZADA", message = "Estado de limpieza no valido") String estado
    ) {}
}

