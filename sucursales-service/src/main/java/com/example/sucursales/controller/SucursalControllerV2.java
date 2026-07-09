package com.example.sucursales.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.sucursales.assemblers.SucursalModelAssembler;
import com.example.sucursales.dto.SucursalRequest;
import com.example.sucursales.model.Sucursal;
import com.example.sucursales.service.SucursalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/sucursales")
public class SucursalControllerV2 {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> getSucursales() {
        List<EntityModel<Sucursal>> sucursales = sucursalService.getSucursales().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (sucursales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<Sucursal>> collectionModel = CollectionModel.of(sucursales,
                linkTo(methodOn(SucursalControllerV2.class).getSucursales()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Sucursal>> getItemPorId(@PathVariable Long id) {
        try {
            Sucursal sucursal = sucursalService.getSucursal(id);
            return ResponseEntity.ok(assembler.toModel(sucursal));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Sucursal>> crearSucursal(@Valid @RequestBody SucursalRequest request) {
        try {
            Sucursal nuevaSucursal = sucursalService.saveSucursal(request);
            return ResponseEntity
                    .created(linkTo(methodOn(SucursalControllerV2.class).getItemPorId(nuevaSucursal.getId())).toUri())
                    .body(assembler.toModel(nuevaSucursal));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Sucursal>> actualizarSucursal(@PathVariable Long id, @Valid @RequestBody SucursalRequest request) {
        Sucursal sucursalActualizada = sucursalService.updateSucursal(id, request);
        return ResponseEntity.ok(assembler.toModel(sucursalActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Long id) {
        sucursalService.deleteSucursal(id);
        return ResponseEntity.noContent().build();
    }
}