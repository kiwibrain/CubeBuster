package com.example.cupones.controller;

import java.util.List;

import com.example.cupones.dto.CuponRequest;
import com.example.cupones.model.Cupon;
import com.example.cupones.service.CuponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cupones")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @GetMapping
    public ResponseEntity<List<Cupon>> getCupones(){
        List<Cupon> cupones = cuponService.getCupones();
        if(cupones.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cupones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemPorId(@PathVariable Long id){
        try {
            Cupon cupon = cuponService.getCupon(id);
            return ResponseEntity.ok(cupon);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearCupon(@Valid @RequestBody CuponRequest request){
        try {
            Cupon nuevoCupon = cuponService.saveCupon(request);
            return ResponseEntity.status(201).body(nuevoCupon);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cupon> actualizarCupon(@PathVariable Long id, @Valid @RequestBody CuponRequest request) {
        Cupon cuponActualizado = cuponService.updateCupon(id, request);
        return ResponseEntity.ok(cuponActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCupon(@PathVariable Long id) {
        cuponService.deleteCupon(id);
        return ResponseEntity.noContent().build();
    }
}