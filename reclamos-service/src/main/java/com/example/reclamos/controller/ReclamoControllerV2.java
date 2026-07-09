package com.example.reclamos.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.reclamos.assemblers.ReclamoModelAssembler;
import com.example.reclamos.dto.ReclamoRequest;
import com.example.reclamos.model.Reclamo;
import com.example.reclamos.service.ReclamoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/reclamos")
public class ReclamoControllerV2 {

    @Autowired
    private ReclamoService reclamoService;

    @Autowired
    private ReclamoModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Reclamo>>> getReclamos() {
        List<EntityModel<Reclamo>> reclamos = reclamoService.getReclamos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());


        CollectionModel<EntityModel<Reclamo>> collectionModel = CollectionModel.of(reclamos,
                linkTo(methodOn(ReclamoControllerV2.class).getReclamos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Reclamo>> getReclamoPorId(@PathVariable Long id) {
        try {
            Reclamo reclamo = reclamoService.getReclamos(id);
            return ResponseEntity.ok(assembler.toModel(reclamo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Reclamo>> crearReclamo(@RequestBody Reclamo reclamo) {
        try {
            Reclamo nuevoReclamo = reclamoService.saveReclamo(reclamo);
            return ResponseEntity
                    .created(linkTo(methodOn(ReclamoControllerV2.class).getReclamoPorId(nuevoReclamo.getId())).toUri())
                    .body(assembler.toModel(nuevoReclamo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Reclamo>> actualizarReclamo(@PathVariable Long id, @Valid @RequestBody ReclamoRequest request) {
        Reclamo reclamoActualizado = reclamoService.updateReclamo(id, request);
        return ResponseEntity.ok(assembler.toModel(reclamoActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReclamo(@PathVariable Long id) {
        reclamoService.deleteReclamo(id);
        return ResponseEntity.noContent().build();
    }
}