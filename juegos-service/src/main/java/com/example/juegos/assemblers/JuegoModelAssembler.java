package com.example.juegos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.juegos.controller.JuegoControllerV2;
import com.example.juegos.model.Juego;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class JuegoModelAssembler implements RepresentationModelAssembler<Juego, EntityModel<Juego>> {

    @Override
    public EntityModel<Juego> toModel(Juego juego) {
        return EntityModel.of(juego,
                linkTo(methodOn(JuegoControllerV2.class).buscarPorId(juego.getIdJuego())).withSelfRel(),
                linkTo(methodOn(JuegoControllerV2.class).listarTodas()).withRel("juegos"));
    }
}

