package com.example.juegos.controller;

import com.example.juegos.dto.JuegoRequest;
import com.example.juegos.model.Juego;
import com.example.juegos.service.JuegoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/juegos") // Ajusta la ruta base según tu proyecto
public class JuegoController {

    @Autowired
    private JuegoService juegoService;

    // GET: Listar todas
    @GetMapping
    public ResponseEntity<List<Juego>> listarTodas() {
        return ResponseEntity.ok(juegoService.getJuegos());
    }

    // GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Juego> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(juegoService.getJuego(id));
    }

    // POST: Crear una película
    @PostMapping
    public ResponseEntity<Juego> crearPelicula(@Valid @RequestBody JuegoRequest request) {
        Juego nuevaPelicula = juegoService.saveJuego(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPelicula);
    }

    // PUT: Actualizar una película existente
    @PutMapping("/{id}")
    public ResponseEntity<Juego> actualizarPelicula(@PathVariable Long id, @Valid @RequestBody JuegoRequest request) {
        Juego peliculaActualizada = juegoService.updateJuego(id, request);
        return ResponseEntity.ok(peliculaActualizada);
    }

    // DELETE: Eliminar una película
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPelicula(@PathVariable Long id) {
        juegoService.deleteJuego(id);
        return ResponseEntity.noContent().build();
    }
}