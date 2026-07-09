package com.example.reservas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.reservas.controller.ReservaControllerV2;
import com.example.reservas.model.Reserva;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ReservaModelAssembler implements RepresentationModelAssembler<Reserva, EntityModel<Reserva>> {

    @Override
    public EntityModel<Reserva> toModel(Reserva reserva) {
        return EntityModel.of(reserva,
                linkTo(methodOn(ReservaControllerV2.class).getReservaPorId(reserva.getId())).withSelfRel(),
                linkTo(methodOn(ReservaControllerV2.class).getReservas()).withRel("reservas"));
    }
}
