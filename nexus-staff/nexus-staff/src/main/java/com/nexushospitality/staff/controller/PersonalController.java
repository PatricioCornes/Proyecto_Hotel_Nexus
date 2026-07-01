package com.nexushospitality.staff.controller;

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

import com.nexushospitality.staff.model.Personal;
import com.nexushospitality.staff.service.PersonalService;
import com.nexushospitality.staff.dto.PersonalDTO;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/empleados")
public class PersonalController {

    @Autowired
    private PersonalService personalService;

    @GetMapping
    public ResponseEntity<List<Personal>> listar() {
        List<Personal> personal = personalService.findAll();
        if (personal.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(personal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personal> buscar(@PathVariable Long id) {
        try {
            Personal personal = personalService.findById(id);
            return ResponseEntity.ok(personal);
        } catch (Exception c) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Personal> guardar(@Valid @RequestBody PersonalDTO dto) {
        Personal personalNuevo = personalService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(personalNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personal> actualizar(@PathVariable Long id, @Valid @RequestBody PersonalDTO dto) {
        try {
            Personal personalActualizado = personalService.update(id, dto);
            return ResponseEntity.ok(personalActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            personalService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
