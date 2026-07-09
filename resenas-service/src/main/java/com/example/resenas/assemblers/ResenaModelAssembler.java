package com.example.resenas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.resenas.controller.ResenaControllerV2;
import com.example.resenas.model.Resena;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ResenaModelAssembler implements RepresentationModelAssembler<Resena, EntityModel<Resena>> {

    @Override
    public EntityModel<Resena> toModel(Resena resena) {
        return EntityModel.of(resena,
                linkTo(methodOn(ResenaControllerV2.class).getItemPorId(resena.getId())).withSelfRel(),
                linkTo(methodOn(ResenaControllerV2.class).getResenas()).withRel("resenas"));
    }
}