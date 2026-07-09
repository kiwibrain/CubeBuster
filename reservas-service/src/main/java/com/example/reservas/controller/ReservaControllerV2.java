package com.example.reservas.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.reservas.assemblers.ReservaModelAssembler;
import com.example.reservas.dto.ReservaRequest;
import com.example.reservas.model.Reserva;
import com.example.reservas.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/reservas")
public class ReservaControllerV2 {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Reserva>>> getReservas() {
        List<EntityModel<Reserva>> reservas = reservaService.getReservas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (reservas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<Reserva>> collectionModel = CollectionModel.of(reservas,
                linkTo(methodOn(ReservaControllerV2.class).getReservas()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Reserva>> getReservaPorId(@PathVariable Long id) {
        Reserva reserva = reservaService.getReserva(id);
        return ResponseEntity.ok(assembler.toModel(reserva));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Reserva>> crearReserva(@Valid @RequestBody ReservaRequest request) {
        Reserva nuevaReserva = reservaService.guardarReserva(request);
        return ResponseEntity
                .created(linkTo(methodOn(ReservaControllerV2.class).getReservaPorId(nuevaReserva.getId())).toUri())
                .body(assembler.toModel(nuevaReserva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Reserva>> actualizarReserva(@PathVariable Long id, @Valid @RequestBody ReservaRequest request) {
        Reserva reservaActualizada = reservaService.updateReserva(id, request);
        return ResponseEntity.ok(assembler.toModel(reservaActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.deleteReserva(id);
        return ResponseEntity.noContent().build();
    }
}