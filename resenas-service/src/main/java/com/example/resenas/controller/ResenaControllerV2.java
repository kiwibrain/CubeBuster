package com.example.resenas.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.resenas.assemblers.ResenaModelAssembler;
import com.example.resenas.dto.ResenaRequest;
import com.example.resenas.model.Resena;
import com.example.resenas.service.ResenaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/resenas")
public class ResenaControllerV2 {

    @Autowired
    private ResenaService resenaService;

    @Autowired
    private ResenaModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Resena>>> getResenas() {
        List<EntityModel<Resena>> resenas = resenaService.getResenas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (resenas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<Resena>> collectionModel = CollectionModel.of(resenas,
                linkTo(methodOn(ResenaControllerV2.class).getResenas()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Resena>> getItemPorId(@PathVariable Long id) {
        try {
            Resena resena = resenaService.getResena(id);
            return ResponseEntity.ok(assembler.toModel(resena));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Resena>> crearResena(@RequestBody ResenaRequest request) {
        try {
            Resena nuevaResena = resenaService.saveResena(request);
            return ResponseEntity
                    .created(linkTo(methodOn(ResenaControllerV2.class).getItemPorId(nuevaResena.getId())).toUri())
                    .body(assembler.toModel(nuevaResena));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Resena>> actualizarResena(@PathVariable Long id, @Valid @RequestBody ResenaRequest request) {
        Resena resenaActualizada = resenaService.updateResena(id, request);
        return ResponseEntity.ok(assembler.toModel(resenaActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        resenaService.deleteResena(id);
        return ResponseEntity.noContent().build();
    }
}