package com.example.reservas.controller;

import java.util.List;

import com.example.reservas.dto.ReservaRequest;
import com.example.reservas.model.Reserva;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.reservas.service.ReservaService;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<Reserva>> getReservas(){
        List<Reserva> reservas = reservaService.getReservas();
        if(reservas.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.getReserva(id));
    }

    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@Valid @RequestBody ReservaRequest request) {
        Reserva nuevaReserva = reservaService.guardarReserva(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable Long id, @Valid @RequestBody ReservaRequest request) {
        Reserva reservaActualizada = reservaService.updateReserva(id, request);
        return ResponseEntity.ok(reservaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
        return ResponseEntity.noContent().build();
    }



}
