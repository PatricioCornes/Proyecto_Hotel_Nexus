package com.Nexus_hospitality_payments.controlador;

import com.Nexus_hospitality_payments.entidad.Pago;
import com.Nexus_hospitality_payments.servicio.PagoServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoControlador {

    private final PagoServicio pagoServicio;

    public PagoControlador(PagoServicio pagoServicio) {
        this.pagoServicio = pagoServicio;
    }

    @GetMapping
    public List<Pago> listar() {
        return pagoServicio.listar();
    }

    @GetMapping("/{id}")
    public Pago obtenerPorId(@PathVariable Long id) {
        return pagoServicio.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pago crear(@Valid @RequestBody PagoRequest request) {
        return pagoServicio.crear(request);
    }

    @PutMapping("/{id}")
    public Pago actualizar(@PathVariable Long id, @Valid @RequestBody PagoRequest request) {
        return pagoServicio.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        pagoServicio.eliminar(id);
    }

    @PostMapping("/reservas/{codigo}/aprobar")
    @ResponseStatus(HttpStatus.CREATED)
    public Pago aprobar(@PathVariable String codigo, @Valid @RequestBody AprobacionRequest request) {
        return pagoServicio.aprobarReserva(codigo, request.monto(), request.metodo());
    }

    @PostMapping("/reservas/{codigo}/reembolso")
    public Pago reembolsar(@PathVariable String codigo, @Valid @RequestBody ReembolsoRequest request) {
        return pagoServicio.reembolsar(codigo, request.porcentaje(), request.motivo());
    }

    public record PagoRequest(
            @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Size(max = 80) String referencia,
            @jakarta.validation.constraints.NotNull @jakarta.validation.constraints.Positive java.math.BigDecimal monto,
            @jakarta.validation.constraints.NotNull @jakarta.validation.constraints.PastOrPresent java.time.LocalDate fecha,
            @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Pattern(regexp = "(?i)EFECTIVO|DEBITO|CREDITO|TRANSFERENCIA", message = "Metodo de pago no valido") String metodo,
            @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Pattern(regexp = "PENDIENTE|APROBADO|RECHAZADO|REEMBOLSADO", message = "Estado de pago no valido") String estado
    ) {}

    public record AprobacionRequest(
            @jakarta.validation.constraints.NotNull @jakarta.validation.constraints.Positive java.math.BigDecimal monto,
            @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Pattern(regexp = "(?i)EFECTIVO|DEBITO|CREDITO|TRANSFERENCIA", message = "Metodo de pago no valido") String metodo) {}

    public record ReembolsoRequest(
            @jakarta.validation.constraints.Min(0) @jakarta.validation.constraints.Max(100) int porcentaje,
            @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Size(max = 250) String motivo) {}

}

