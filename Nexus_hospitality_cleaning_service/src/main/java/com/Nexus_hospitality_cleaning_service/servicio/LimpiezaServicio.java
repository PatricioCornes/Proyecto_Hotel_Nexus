package com.Nexus_hospitality_cleaning_service.servicio;

import com.Nexus_hospitality_cleaning_service.controlador.LimpiezaControlador;
import com.Nexus_hospitality_cleaning_service.entidad.Limpieza;
import com.Nexus_hospitality_cleaning_service.repositorio.LimpiezaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LimpiezaServicio {

    private final LimpiezaRepositorio limpiezaRepositorio;

    public LimpiezaServicio(LimpiezaRepositorio limpiezaRepositorio) {
        this.limpiezaRepositorio = limpiezaRepositorio;
    }

    @Transactional(readOnly = true)
    public List<Limpieza> listar() {
        return limpiezaRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Limpieza obtenerPorId(Long id) {
        return limpiezaRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Limpieza no encontrada con id=" + id));
    }

    @Transactional
    public Limpieza crear(LimpiezaControlador.LimpiezaRequest request) {
        validarReferencia(request.referencia(), null);

        Limpieza limpieza = Limpieza.builder()
                .referencia(request.referencia())
                .habitacionId(request.habitacionId())
                .fecha(request.fecha())
                .tipo(request.tipo())
                .estado(request.estado())
                .build();

        return limpiezaRepositorio.save(limpieza);
    }

    @Transactional
    public Limpieza actualizar(Long id, LimpiezaControlador.LimpiezaRequest request) {
        Limpieza existente = obtenerPorId(id);

        validarReferencia(request.referencia(), existente.getId());

        existente.setReferencia(request.referencia());
        existente.setHabitacionId(request.habitacionId());
        existente.setFecha(request.fecha());
        existente.setTipo(request.tipo());
        existente.setEstado(request.estado());

        return limpiezaRepositorio.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        if (!limpiezaRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Limpieza no encontrada con id=" + id);
        }
        limpiezaRepositorio.deleteById(id);
    }

    private void validarReferencia(String referencia, Long idActual) {
        Optional<Limpieza> posible = limpiezaRepositorio.findByReferencia(referencia);
        if (posible.isPresent()) {
            if (idActual == null || !posible.get().getId().equals(idActual)) {
                throw new IllegalArgumentException("Ya existe una limpieza con referencia=" + referencia);
            }
        }
    }
}

