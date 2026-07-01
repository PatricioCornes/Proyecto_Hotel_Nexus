package com.nexus.hospitality_reservation.controller;

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

import com.nexus.hospitality_reservation.service.ReservacionService;

import jakarta.validation.Valid;

import com.nexus.hospitality_reservation.dto.*;
import com.nexus.hospitality_reservation.model.Reservacion;

import java.util.List;


@RestController
@RequestMapping("/reservaciones")
public class ReservaController {

    // Servicio inyectado que contiene la lógica de negocio
    @Autowired
    private ReservacionService reservacionService;




    @GetMapping
    public ResponseEntity<List<Reservacion>> listar() {
        List<Reservacion> reservacion = reservacionService.findAll();
        if (reservacion.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(reservacion); // 200 OK
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReservacionDTO> buscar(@PathVariable Long id) {
        try {
            ReservacionDTO detalle = reservacionService.obtenerReservacionConDetalle(id);
            return ResponseEntity.ok(detalle);
        } catch (Exception c) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }



    @PostMapping
    public ResponseEntity<Reservacion> guardar(@Valid @RequestBody ReservacionDTO dto) {
        Reservacion reservacionNueva = reservacionService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservacionNueva); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservacion> actualizar(@PathVariable Long id, @Valid @RequestBody ReservacionDTO dto) {
        try {
            Reservacion reservacionActual = reservacionService.update(id, dto);
            return ResponseEntity.ok(reservacionActual); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            reservacionService.delete(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }    


}
