package com.Nexus_hospitality_payments.servicio;

import com.Nexus_hospitality_payments.controlador.PagoControlador;
import com.Nexus_hospitality_payments.entidad.Pago;
import com.Nexus_hospitality_payments.repositorio.PagoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.Nexus_hospitality_payments.event.PaymentEventPublisher;

@Service
public class PagoServicio {

    private final PagoRepositorio pagoRepositorio;
    private final PaymentEventPublisher events;

    public PagoServicio(PagoRepositorio pagoRepositorio, PaymentEventPublisher events) {
        this.pagoRepositorio = pagoRepositorio;
        this.events = events;
    }

    @Transactional(readOnly = true)
    public List<Pago> listar() {
        return pagoRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Pago obtenerPorId(Long id) {
        return pagoRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado con id=" + id));
    }

    @Transactional
    public Pago crear(PagoControlador.PagoRequest request) {
        validarReferencia(request.referencia(), null);

        Pago pago = Pago.builder()
                .referencia(request.referencia())
                .monto(request.monto())
                .fecha(request.fecha())
                .metodo(request.metodo())
                .estado(request.estado())
                .tipo("COBRO")
                .build();

        return pagoRepositorio.save(pago);
    }

    @Transactional
    public Pago actualizar(Long id, PagoControlador.PagoRequest request) {
        Pago existente = obtenerPorId(id);

        validarReferencia(request.referencia(), existente.getId());

        existente.setReferencia(request.referencia());
        existente.setMonto(request.monto());
        existente.setFecha(request.fecha());
        existente.setMetodo(request.metodo());
        existente.setEstado(request.estado());

        return pagoRepositorio.save(existente);
    }

    @Transactional
    public Pago aprobarReserva(String reservaCodigo, java.math.BigDecimal monto, String metodo) {
        Optional<Pago> existente = pagoRepositorio.findByReservaCodigo(reservaCodigo).stream()
                .filter(p -> "COBRO".equals(p.getTipo()) && "APROBADO".equals(p.getEstado()))
                .findFirst();
        if (existente.isPresent()) return existente.get();
        Pago pago = Pago.builder()
                .referencia("PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .reservaCodigo(reservaCodigo)
                .monto(monto)
                .fecha(java.time.LocalDate.now())
                .metodo(metodo)
                .estado("APROBADO")
                .tipo("COBRO")
                .build();
        Pago guardado = pagoRepositorio.save(pago);
        events.paymentCompleted(guardado.getReferencia(), reservaCodigo, true);
        return guardado;
    }

    @Transactional
    public Pago reembolsar(String reservaCodigo, int porcentaje, String motivo) {
        java.math.BigDecimal aprobado = pagoRepositorio.findByReservaCodigo(reservaCodigo).stream()
                .filter(p -> "COBRO".equals(p.getTipo()) && "APROBADO".equals(p.getEstado()))
                .map(Pago::getMonto).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
        Pago reembolso = Pago.builder()
                .referencia("REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .reservaCodigo(reservaCodigo)
                .monto(aprobado.multiply(java.math.BigDecimal.valueOf(porcentaje)).divide(java.math.BigDecimal.valueOf(100)))
                .fecha(java.time.LocalDate.now())
                .metodo(motivo)
                .estado(porcentaje == 0 ? "SIN_REEMBOLSO" : "REEMBOLSADO")
                .tipo("REEMBOLSO")
                .build();
        return pagoRepositorio.save(reembolso);
    }

    @Transactional(readOnly = true)
    public java.math.BigDecimal totalPagado(String reservaCodigo) {
        return pagoRepositorio.findByReservaCodigo(reservaCodigo).stream()
                .map(p -> {
                    if ("COBRO".equals(p.getTipo()) && ("APROBADO".equals(p.getEstado()) || "Pagado".equalsIgnoreCase(p.getEstado())))
                        return p.getMonto();
                    if ("REEMBOLSO".equals(p.getTipo()) && "REEMBOLSADO".equals(p.getEstado()))
                        return p.getMonto().negate();
                    return java.math.BigDecimal.ZERO;
                })
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!pagoRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Pago no encontrado con id=" + id);
        }
        pagoRepositorio.deleteById(id);
    }

    private void validarReferencia(String referencia, Long idActual) {
        Optional<Pago> posible = pagoRepositorio.findByReferencia(referencia);
        if (posible.isPresent()) {
            if (idActual == null || !posible.get().getId().equals(idActual)) {
                throw new IllegalStateException("Ya existe un pago con referencia=" + referencia);
            }
        }
    }
}

