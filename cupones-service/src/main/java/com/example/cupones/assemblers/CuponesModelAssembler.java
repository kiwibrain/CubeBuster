package com.example.cupones.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.cupones.controller.CuponControllerV2;
import com.example.cupones.model.Cupon;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CuponesModelAssembler implements RepresentationModelAssembler<Cupon, EntityModel<Cupon>> {

    @Override
    public EntityModel<Cupon> toModel(Cupon cupon) {
        return EntityModel.of(cupon,
                linkTo(methodOn(CuponControllerV2.class).getItemPorId(cupon.getId())).withSelfRel(),
                linkTo(methodOn(CuponControllerV2.class).getCupones()).withRel("cupones"));
    }
}


