package com.example.resenas.controller;

import java.util.List;

import com.example.resenas.dto.ResenaRequest;
import com.example.resenas.model.Resena;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.resenas.service.ResenaService;

@RestController
@RequestMapping("/api/v1/resenas")
public class ResenaController {
    @Autowired
    private ResenaService resenaService;

    @GetMapping
    public ResponseEntity<List<Resena>> getResenas(){
        List<Resena> resenas = resenaService.getResenas();
        if(resenas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resenas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemPorId(@PathVariable Long id){
        try {
            Resena resena = resenaService.getResena(id);
            return ResponseEntity.ok(resena);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearResena(@RequestBody ResenaRequest request){
        try {
            Resena nuevaResena = resenaService.saveResena(request);
            return ResponseEntity.status(201).body(nuevaResena);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resena> actualizarResena(@PathVariable Long id, @Valid @RequestBody ResenaRequest request) {
        Resena resenaActualizada = resenaService.updateResena(id, request);
        return ResponseEntity.ok(resenaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        resenaService.deleteResena(id);
        return ResponseEntity.noContent().build();
    }

}
