package com.Nexus_hospitality_payments.controlador;

import com.Nexus_hospitality_payments.servicio.FacturacionServicio;
import com.Nexus_hospitality_payments.entidad.FacturaFinal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facturas")
public class FacturacionControlador {
    private final FacturacionServicio servicio;
    public FacturacionControlador(FacturacionServicio servicio) { this.servicio = servicio; }

    @GetMapping("/reserva/{codigo}")
    public FacturaFinal obtener(@PathVariable String codigo) {
        return servicio.consolidar(codigo);
    }
}
