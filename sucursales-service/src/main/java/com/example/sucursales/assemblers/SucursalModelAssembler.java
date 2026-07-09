package com.example.sucursales.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.sucursales.controller.SucursalControllerV2;
import com.example.sucursales.model.Sucursal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(sucursal,
                linkTo(methodOn(SucursalControllerV2.class).getItemPorId(sucursal.getId())).withSelfRel(),
                linkTo(methodOn(SucursalControllerV2.class).getSucursales()).withRel("sucursales"));
    }
}
