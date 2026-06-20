package com.example.multas.controller;

import java.util.List;

import com.example.multas.dto.MultaRequest;
import com.example.multas.model.Multa;
import com.example.multas.service.MultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/multas")
public class MultaController {
    @Autowired
    private MultaService multaService;

    @GetMapping
    public ResponseEntity<List<Multa>> getMultas(){
        List<Multa> multas = multaService.getMultas();
        if(multas.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(multas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemPorId(@PathVariable Long id){
        try {
            Multa multa = multaService.getMulta(id);
            return ResponseEntity.ok(multa);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearMulta(@RequestBody MultaRequest request){
        try {
            Multa nuevaMulta = multaService.saveMultas(request);
            return ResponseEntity.status(201).body(nuevaMulta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Multa> actualizarMulta(@PathVariable Long id, @Valid @RequestBody MultaRequest request) {
        Multa multaActualizada = multaService.updateMulta(id, request);
        return ResponseEntity.ok(multaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMulta(@PathVariable Long id) {
        multaService.eliminarMulta(id);
        return ResponseEntity.noContent().build();
    }

}
