package com.example.multas.assemblers;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.multas.controller.MultaControllerV2;
import com.example.multas.model.Multa;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class MultasModelAssembler implements RepresentationModelAssembler<Multa, EntityModel<Multa>> {

    @Override
    public EntityModel<Multa> toModel(Multa multa) {
        return EntityModel.of(multa,
                linkTo(methodOn(MultaControllerV2.class).getItemPorId(multa.getId())).withSelfRel(),
                linkTo(methodOn(MultaControllerV2.class).getMultas()).withRel("multas"));
    }
}
