package com.example.reclamos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.reclamos.controller.ReclamoControllerV2;
import com.example.reclamos.model.Reclamo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ReclamoModelAssembler implements RepresentationModelAssembler<Reclamo, EntityModel<Reclamo>> {

    @Override
    public EntityModel<Reclamo> toModel(Reclamo reclamo) {
        return EntityModel.of(reclamo,
                linkTo(methodOn(ReclamoControllerV2.class).getReclamoPorId(reclamo.getId())).withSelfRel(),
                linkTo(methodOn(ReclamoControllerV2.class).getReclamos()).withRel("reclamos"));
    }
}
