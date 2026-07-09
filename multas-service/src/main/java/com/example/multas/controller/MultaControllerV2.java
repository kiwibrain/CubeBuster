package com.example.multas.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.multas.assemblers.MultasModelAssembler;
import com.example.multas.dto.MultaRequest;
import com.example.multas.model.Multa;
import com.example.multas.service.MultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/multas")
public class MultaControllerV2 {

    @Autowired
    private MultaService multaService;

    @Autowired
    private MultasModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Multa>>> getMultas() {
        List<EntityModel<Multa>> multas = multaService.getMultas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (multas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<Multa>> collectionModel = CollectionModel.of(multas,
                linkTo(methodOn(MultaController.class).getMultas()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Multa>> getItemPorId(@PathVariable Long id) {
        try {
            Multa multa = multaService.getMulta(id);
            return ResponseEntity.ok(assembler.toModel(multa));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Multa>> crearMulta(@RequestBody MultaRequest request) {
        try {
            Multa nuevaMulta = multaService.saveMultas(request);
            return ResponseEntity
                    .created(linkTo(methodOn(MultaController.class).getItemPorId(nuevaMulta.getId())).toUri())
                    .body(assembler.toModel(nuevaMulta));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Multa>> actualizarMulta(@PathVariable Long id, @Valid @RequestBody MultaRequest request) {
        Multa multaActualizada = multaService.updateMulta(id, request);
        return ResponseEntity.ok(assembler.toModel(multaActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMulta(@PathVariable Long id) {
        multaService.eliminarMulta(id);
        return ResponseEntity.noContent().build();
    }
}
