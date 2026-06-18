package com.example.peliculas.controller;

import com.example.peliculas.dto.PeliculaRequest;
import com.example.peliculas.model.Pelicula;
import com.example.peliculas.service.PeliculaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/peliculas") // Ajusta la ruta base según tu proyecto
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    // GET: Listar todas
    @GetMapping
    public ResponseEntity<List<Pelicula>> listarTodas() {
        return ResponseEntity.ok(peliculaService.getPeliculas());
    }

    // GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(peliculaService.getPelicula(id));
    }

    // POST: Crear una película
    @PostMapping
    public ResponseEntity<Pelicula> crearPelicula(@Valid @RequestBody PeliculaRequest request) {
        Pelicula nuevaPelicula = peliculaService.savePelicula(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPelicula);
    }

    // PUT: Actualizar una película existente
    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> actualizarPelicula(@PathVariable Long id, @Valid @RequestBody PeliculaRequest request) {
        Pelicula peliculaActualizada = peliculaService.updatePelicula(id, request);
        return ResponseEntity.ok(peliculaActualizada);
    }

    // DELETE: Eliminar una película
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPelicula(@PathVariable Long id) {
        peliculaService.deletePelicula(id);
        return ResponseEntity.noContent().build();
    }
}