package com.example.juegos.controller;

import com.example.juegos.assemblers.JuegoModelAssembler;
import com.example.juegos.dto.JuegoRequest;
import com.example.juegos.model.Juego;
import com.example.juegos.service.JuegoService;
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
@RequestMapping("/api/v2/juegos")
public class JuegoControllerV2 {

    @Autowired
    private JuegoService juegoService;

    @Autowired
    private JuegoModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Juego>>> listarTodas() {
        List<EntityModel<Juego>> juegos = juegoService.getJuegos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Juego>> collectionModel = CollectionModel.of(juegos,
                linkTo(methodOn(JuegoControllerV2.class).listarTodas()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Juego>> buscarPorId(@PathVariable Long id) {
        Juego juego = juegoService.getJuego(id);
        return ResponseEntity.ok(assembler.toModel(juego));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Juego>> crearJuego(@Valid @RequestBody JuegoRequest request) {
        Juego nuevoJuego = juegoService.saveJuego(request);
        return ResponseEntity
                .created(linkTo(methodOn(JuegoControllerV2.class).buscarPorId(nuevoJuego.getIdJuego())).toUri())
                .body(assembler.toModel(nuevoJuego));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Juego>> actualizarJuego(@PathVariable Long id, @Valid @RequestBody JuegoRequest request) {
        Juego juegoActualizado = juegoService.updateJuego(id, request);
        return ResponseEntity.ok(assembler.toModel(juegoActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJuego(@PathVariable Long id) {
        juegoService.deleteJuego(id);
        return ResponseEntity.noContent().build();
    }
}
