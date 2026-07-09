package com.example.pagos.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.pagos.assemblers.PagoModelAssembler;
import com.example.pagos.dto.PagoRequest;
import com.example.pagos.model.Pago;
import com.example.pagos.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/pagos")
public class PagoControllerV2 {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Pago>>> getPagos() {
        List<EntityModel<Pago>> pagos = pagoService.getPagos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<Pago>> collectionModel = CollectionModel.of(pagos,
                linkTo(methodOn(PagoController.class).getPagos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pago>> getItemPorId(@PathVariable Long id) {
        try {
            Pago pago = pagoService.getPago(id);
            return ResponseEntity.ok(assembler.toModel(pago));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Pago>> crearPago(@RequestBody PagoRequest request) {
        try {
            Pago nuevoPago = pagoService.savePago(request);
            return ResponseEntity
                    .created(linkTo(methodOn(PagoController.class).getItemPorId(nuevoPago.getId())).toUri())
                    .body(assembler.toModel(nuevoPago));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Pago>> actualizarPago(@PathVariable Long id, @Valid @RequestBody PagoRequest request) {
        Pago pagoActualizado = pagoService.updatePago(id, request);
        return ResponseEntity.ok(assembler.toModel(pagoActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.deletePago(id);
        return ResponseEntity.noContent().build();
    }
}