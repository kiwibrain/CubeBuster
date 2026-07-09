package com.example.peliculas.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.peliculas.controller.PeliculaControllerV2;
import com.example.peliculas.model.Pelicula;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PeliculaModelAssembler implements RepresentationModelAssembler<Pelicula, EntityModel<Pelicula>> {

    @Override
    public EntityModel<Pelicula> toModel(Pelicula pelicula) {
        return EntityModel.of(pelicula,
                linkTo(methodOn(PeliculaControllerV2.class).buscarPorId(pelicula.getIdPelicula())).withSelfRel(),
                linkTo(methodOn(PeliculaControllerV2.class).listarTodas()).withRel("peliculas"));
    }
}