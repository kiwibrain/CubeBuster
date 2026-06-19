package com.example.cupones.controller;

import java.util.List;

import com.example.cupones.dto.PagoRequest;
import com.example.cupones.model.Pago;
import com.example.cupones.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<Pago>> getPagos(){
        List<Pago> pagos = pagoService.getPagos();
        if(pagos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemPorId(@PathVariable Long id){
        try {
            Pago pago = pagoService.getPago(id);
            return ResponseEntity.ok(pago);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearPago(@RequestBody PagoRequest request){
        try {
            Pago nuevoPago = pagoService.savePago(request);
            return ResponseEntity.status(201).body(nuevoPago);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizarPago(@PathVariable Long id, @Valid @RequestBody PagoRequest request) {
        Pago pagoActualizado = pagoService.updatePago(id, request);
        return ResponseEntity.ok(pagoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.deletePago(id);
        return ResponseEntity.noContent().build();
    }
}