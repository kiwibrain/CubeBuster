package com.example.cupones.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.cupones.assemblers.CuponesModelAssembler;
import com.example.cupones.dto.CuponRequest;
import com.example.cupones.model.Cupon;
import com.example.cupones.service.CuponService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/cupones")
public class CuponControllerV2 {

    @Autowired
    private CuponService cuponService;

    @Autowired
    private CuponesModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Cupon>>> getCupones() {
        List<EntityModel<Cupon>> cupones = cuponService.getCupones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());


        CollectionModel<EntityModel<Cupon>> collectionModel = CollectionModel.of(cupones,
                linkTo(methodOn(CuponController.class).getCupones()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Cupon>> getItemPorId(@PathVariable Long id) {
        try {
            Cupon cupon = cuponService.getCupon(id);
            return ResponseEntity.ok(assembler.toModel(cupon));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Cupon>> crearCupon(@Valid @RequestBody CuponRequest request) {
        try {
            Cupon nuevoCupon = cuponService.saveCupon(request);
            return ResponseEntity
                    .created(linkTo(methodOn(CuponController.class).getItemPorId(nuevoCupon.getId())).toUri())
                    .body(assembler.toModel(nuevoCupon));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Cupon>> actualizarCupon(@PathVariable Long id, @Valid @RequestBody CuponRequest request) {
        Cupon cuponActualizado = cuponService.updateCupon(id, request);
        return ResponseEntity.ok(assembler.toModel(cuponActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCupon(@PathVariable Long id) {
        cuponService.deleteCupon(id);
        return ResponseEntity.noContent().build();
    }
}