package com.Nexus_hospitality_garage.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Nexus_hospitality_garage.dto.*;
import com.Nexus_hospitality_garage.model.Estacionamiento;
import com.Nexus_hospitality_garage.repository.*;

import jakarta.transaction.Transactional;


@Transactional
@Service
public class EstacionamientoService {

    @Autowired
    private EstacionamientoRepository estacionamientoRepository;
    
    public List<Estacionamiento> findAll() {
        return estacionamientoRepository.findAll();
    }

    public Estacionamiento findById(Long id) {
        return estacionamientoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estacionamiento no encontrado"));
    }

    public Estacionamiento save(EstacionamientoDTO dto) {
        Estacionamiento estacionamiento = new Estacionamiento();
        
        estacionamiento.setCodigoPlaza(dto.getCodigoPlaza());
        estacionamiento.setEstado(dto.getEstado());
        estacionamiento.setPatenteVehiculo(dto.getPatenteVehiculo());
        estacionamiento.setTipoVehiculo(dto.getTipoVehiculo());
        estacionamiento.setFechaIngreso(dto.getFechaIngreso());
        estacionamiento.setHabitacionHuesped(dto.getHabitacionHuesped());
        estacionamiento.setNombreContacto(dto.getNombreContacto());
        estacionamiento.setObservaciones(dto.getObservaciones());
        
        return estacionamientoRepository.save(estacionamiento);
    }

    public Estacionamiento update(Long id, EstacionamientoDTO dto) {
        Estacionamiento estacionamiento = findById(id); 
        
        estacionamiento.setEstado(dto.getEstado());
        estacionamiento.setPatenteVehiculo(dto.getPatenteVehiculo());
        estacionamiento.setTipoVehiculo(dto.getTipoVehiculo());
        estacionamiento.setFechaIngreso(dto.getFechaIngreso());
        estacionamiento.setHabitacionHuesped(dto.getHabitacionHuesped());
        estacionamiento.setNombreContacto(dto.getNombreContacto());
        estacionamiento.setObservaciones(dto.getObservaciones());

        return estacionamientoRepository.save(estacionamiento);
    }

    public void delete(Long id) {
        findById(id);
        estacionamientoRepository.deleteById(id);
    }

}
