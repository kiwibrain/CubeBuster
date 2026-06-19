package com.example.resenas.controller;

import java.util.List;

import com.example.resenas.dto.ReclamoRequest;
import com.example.resenas.model.Reclamo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.resenas.service.ReclamoService;

@RestController
@RequestMapping("/api/v1/reclamos")
public class ReclamoController {
    @Autowired
    private ReclamoService reclamoService;

    @GetMapping
    public ResponseEntity<List<Reclamo>> getReclamos(){
        List<Reclamo> reclamos = reclamoService.getReclamos();
        if(reclamos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reclamos);
    }

    @PostMapping
    public ResponseEntity<?> crearReclamo(@RequestBody Reclamo reclamo){
        try {
            Reclamo reclamo2 = reclamoService.saveReclamo(reclamo);
            return ResponseEntity.status(201).body(reclamo2);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPedidoPorId(@PathVariable Long id){
        try {
            Reclamo reclamo = reclamoService.getReclamos(id);
            return ResponseEntity.ok(reclamo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reclamo> actualizarReclamo(@PathVariable Long id, @Valid @RequestBody ReclamoRequest request) {
        Reclamo reclamoActualizado = reclamoService.updateReclamo(id, request);
        return ResponseEntity.ok(reclamoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReclamo(@PathVariable Long id) {
        reclamoService.deleteReclamo(id);
        return ResponseEntity.noContent().build();
    }

}
