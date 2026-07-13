package com.nexus.hospitality_reservation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "nexus-payments")
public interface PagoClient {
    @PostMapping("/pagos/reservas/{codigo}/reembolso")
    void reembolsar(@PathVariable String codigo, @RequestBody ReembolsoRequest request);

    record ReembolsoRequest(int porcentaje, String motivo) {}
}
