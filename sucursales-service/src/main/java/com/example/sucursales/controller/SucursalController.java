package com.example.sucursales.controller;

import java.util.List;

import com.example.sucursales.dto.SucursalRequest;
import com.example.sucursales.model.Sucursal;
import com.example.sucursales.service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<Sucursal>> getSucursales(){
        List<Sucursal> sucursales = sucursalService.getSucursales();
        if(sucursales.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sucursales);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemPorId(@PathVariable Long id){
        try {
            Sucursal sucursal = sucursalService.getSucursal(id);
            return ResponseEntity.ok(sucursal);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearSucursal(@Valid @RequestBody SucursalRequest request){
        try {
            Sucursal nuevaSucursal = sucursalService.saveSucursal(request);
            return ResponseEntity.status(201).body(nuevaSucursal);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> actualizarSucursal(@PathVariable Long id, @Valid @RequestBody SucursalRequest request) {
        Sucursal sucursalActualizada = sucursalService.updateSucursal(id, request);
        return ResponseEntity.ok(sucursalActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
        sucursalService.deleteSucursal(id);
        return ResponseEntity.noContent().build();
    }
}