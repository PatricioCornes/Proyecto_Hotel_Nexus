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

    /**
     * PUT /pagos/{id}/aprobar
     * Endpoint de orquestación para confirmar la reservación asociada (por codigoReserva)
     */
    @PutMapping("/{id}/aprobar")
    public Pago aprobar(@PathVariable Long id) {
        return pagoServicio.aprobarPagoYConfirmarReservacion(id);
    }

    public record PagoRequest(

            @jakarta.validation.constraints.NotBlank String referencia,
            @jakarta.validation.constraints.NotNull @jakarta.validation.constraints.PositiveOrZero java.math.BigDecimal monto,
            @jakarta.validation.constraints.NotNull java.time.LocalDate fecha,
            @jakarta.validation.constraints.NotBlank String metodo,
            @jakarta.validation.constraints.NotBlank String estado,
            // Código de reserva para poder confirmar una reservación al aprobar el pago
            String codigoReserva
    ) {}



}

