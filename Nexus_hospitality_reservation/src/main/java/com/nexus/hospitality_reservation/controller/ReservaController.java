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
import java.math.BigDecimal;


@RestController
@RequestMapping("/reservaciones")
public class ReservaController {

    @Autowired
    private ReservacionService reservacionService;




    @GetMapping
    public ResponseEntity<List<Reservacion>> listar() {
        List<Reservacion> reservacion = reservacionService.findAll();
        if (reservacion.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservacion);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReservacionDTO> buscar(@PathVariable Long id) {
        try {
            ReservacionDTO detalle = reservacionService.obtenerReservacionConDetalle(id);
            return ResponseEntity.ok(detalle);
        } catch (Exception c) {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping
    public ResponseEntity<Reservacion> guardar(@Valid @RequestBody ReservacionDTO dto) {
        Reservacion reservacionNueva = reservacionService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservacionNueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservacion> actualizar(@PathVariable Long id, @Valid @RequestBody ReservacionDTO dto) {
        try {
            Reservacion reservacionActual = reservacionService.update(id, dto);
            return ResponseEntity.ok(reservacionActual);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            reservacionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }    

    @PostMapping("/{id}/check-in")
    public ResponseEntity<Reservacion> checkIn(@PathVariable Long id) {
        return ResponseEntity.ok(reservacionService.checkIn(id));
    }

    @PostMapping("/{id}/check-out")
    public ResponseEntity<Reservacion> checkOut(@PathVariable Long id) {
        return ResponseEntity.ok(reservacionService.checkOut(id));
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Reservacion> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(reservacionService.cancelar(id));
    }

    @GetMapping("/interno/{codigo}/cargo-habitacion")
    public BigDecimal cargoHabitacion(@PathVariable String codigo) {
        return reservacionService.cargoHabitacion(codigo);
    }


}
