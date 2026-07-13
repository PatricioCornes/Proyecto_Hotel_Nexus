package com.Nexus_hospitality_payments.servicio;

import com.Nexus_hospitality_payments.entidad.FacturaFinal;
import com.Nexus_hospitality_payments.repositorio.FacturaFinalRepositorio;
import com.Nexus_hospitality_payments.repositorio.CargoAdicionalRepositorio;
import com.Nexus_hospitality_payments.entidad.CargoAdicional;
import com.Nexus_hospitality_payments.config.ServiceUrlResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class FacturacionServicio {
    private final RestClient client;
    private final ServiceUrlResolver resolver;
    private final PagoServicio pagos;
    private final FacturaFinalRepositorio facturas;
    private final CargoAdicionalRepositorio cargos;

    public FacturacionServicio(RestClient.Builder builder, PagoServicio pagos, FacturaFinalRepositorio facturas,
            CargoAdicionalRepositorio cargos, ServiceUrlResolver resolver) {
        this.client = builder.build();
        this.resolver = resolver;
        this.pagos = pagos;
        this.facturas = facturas;
        this.cargos = cargos;
    }

    @Transactional
    public FacturaFinal consolidar(String codigo) {
        BigDecimal habitacion = get("nexus-reservation", "/reservaciones/interno/{codigo}/cargo-habitacion", codigo);
        BigDecimal consumos = get("nexus-restaurant", "/consumos/reserva/{codigo}/total", codigo);
        BigDecimal estacionamiento = get("nexus-garage", "/usos-estacionamiento/reserva/{codigo}/total", codigo);
        BigDecimal adicionales = cargos.findByReservaCodigo(codigo).stream()
                .map(CargoAdicional::getMonto).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal total = habitacion.add(consumos).add(estacionamiento).add(adicionales);
        BigDecimal pagado = pagos.totalPagado(codigo);
        FacturaFinal factura = facturas.findByReservaCodigo(codigo).orElseGet(FacturaFinal::new);
        factura.setReservaCodigo(codigo);
        factura.setHabitacion(habitacion);
        factura.setRestaurante(consumos);
        factura.setEstacionamiento(estacionamiento);
        factura.setServiciosAdicionales(adicionales);
        factura.setTotal(total);
        factura.setPagado(pagado);
        factura.setSaldo(total.subtract(pagado).max(BigDecimal.ZERO));
        factura.setGeneradaEn(LocalDateTime.now());
        return facturas.save(factura);
    }

    private BigDecimal get(String serviceId, String path, String codigo) {
        BigDecimal value = client.get().uri(resolver.resolve(serviceId) + path, codigo).retrieve().body(BigDecimal.class);
        return value == null ? BigDecimal.ZERO : value;
    }
}
