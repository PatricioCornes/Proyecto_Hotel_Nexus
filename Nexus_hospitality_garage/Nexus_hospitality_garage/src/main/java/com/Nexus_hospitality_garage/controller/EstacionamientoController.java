package com.Nexus_hospitality_garage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Nexus_hospitality_garage.dto.EstacionamientoDTO;
import com.Nexus_hospitality_garage.model.Estacionamiento;
import com.Nexus_hospitality_garage.service.EstacionamientoService;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/estacionamiento")
public class EstacionamientoController {

    @Autowired
    private EstacionamientoService estacionamientoService;

    @GetMapping
    public ResponseEntity<List<Estacionamiento>> listar() {
        List<Estacionamiento> estacionamientos = estacionamientoService.findAll();
        if (estacionamientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estacionamientos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estacionamiento> buscar(@PathVariable Long id) {
        try {
            Estacionamiento estacionamiento = estacionamientoService.findById(id);
            return ResponseEntity.ok(estacionamiento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Captura si el Service lanza RuntimeException
        }
    }

    @PostMapping
    public ResponseEntity<Estacionamiento> guardar(@Valid @RequestBody EstacionamientoDTO dto) {
        Estacionamiento estacionamientoNuevo = estacionamientoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(estacionamientoNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estacionamiento> actualizar(@PathVariable Long id, @Valid @RequestBody EstacionamientoDTO dto) {
        try {
            Estacionamiento estacionamientoActualizado = estacionamientoService.update(id, dto);
            return ResponseEntity.ok(estacionamientoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            estacionamientoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
