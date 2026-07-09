package com.example.peliculas.controller;

import com.example.peliculas.assemblers.PeliculaModelAssembler;
import com.example.peliculas.dto.PeliculaRequest;
import com.example.peliculas.model.Pelicula;
import com.example.peliculas.service.PeliculaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/peliculas")
public class PeliculaControllerV2 {

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private PeliculaModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Pelicula>>> listarTodas() {
        List<EntityModel<Pelicula>> peliculas = peliculaService.getPeliculas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Pelicula>> collectionModel = CollectionModel.of(peliculas,
                linkTo(methodOn(PeliculaControllerV2.class).listarTodas()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pelicula>> buscarPorId(@PathVariable Long id) {
        Pelicula pelicula = peliculaService.getPelicula(id);
        return ResponseEntity.ok(assembler.toModel(pelicula));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Pelicula>> crearPelicula(@Valid @RequestBody PeliculaRequest request) {
        Pelicula nuevaPelicula = peliculaService.savePelicula(request);
        return ResponseEntity
                .created(linkTo(methodOn(PeliculaControllerV2.class).buscarPorId(nuevaPelicula.getIdPelicula())).toUri())
                .body(assembler.toModel(nuevaPelicula));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Pelicula>> actualizarPelicula(@PathVariable Long id, @Valid @RequestBody PeliculaRequest request) {
        Pelicula peliculaActualizada = peliculaService.updatePelicula(id, request);
        return ResponseEntity.ok(assembler.toModel(peliculaActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPelicula(@PathVariable Long id) {
        peliculaService.deletePelicula(id);
        return ResponseEntity.noContent().build();
    }
}