package com.Nexus_hospitality_payments.servicio;

import com.Nexus_hospitality_payments.controlador.PagoControlador;
import com.Nexus_hospitality_payments.entidad.Pago;
import com.Nexus_hospitality_payments.repositorio.PagoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.web.client.RestTemplate;

@Service
public class PagoServicio {

    private final PagoRepositorio pagoRepositorio;
    private final RestTemplate restTemplate = new RestTemplate();


    private static final String RESERVATION_BASE_URL = "http://localhost:8080";

    public PagoServicio(PagoRepositorio pagoRepositorio) {
        this.pagoRepositorio = pagoRepositorio;
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
                .codigoReserva(request.codigoReserva())
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
        existente.setCodigoReserva(request.codigoReserva());


        return pagoRepositorio.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!pagoRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Pago no encontrado con id=" + id);
        }
        pagoRepositorio.deleteById(id);
    }

    public Pago aprobarPagoYConfirmarReservacion(Long id) {
        Pago pago = obtenerPorId(id);

        
        pago.setEstado("pago realizado correctamente");
        Pago guardado = pagoRepositorio.save(pago);

        
        if (pago.getCodigoReserva() == null || pago.getCodigoReserva().isBlank()) {
            throw new IllegalArgumentException("Pago no tiene codigoReserva para confirmar la reservación");
        }

        String url = RESERVATION_BASE_URL + "/api/v1/reservaciones/confirmar?codigoReserva=" + pago.getCodigoReserva();
        restTemplate.postForEntity(url, null, Object.class);

        return guardado;
    }

    private void validarReferencia(String referencia, Long idActual) {

        Optional<Pago> posible = pagoRepositorio.findByReferencia(referencia);
        if (posible.isPresent()) {
            if (idActual == null || !posible.get().getId().equals(idActual)) {
                throw new IllegalArgumentException("Ya existe un pago con referencia=" + referencia);
            }
        }
    }
}


