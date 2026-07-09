package com.example.pagos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.pagos.controller.PagoControllerV2;
import com.example.pagos.model.Pago;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<Pago, EntityModel<Pago>> {

    @Override
    public EntityModel<Pago> toModel(Pago pago) {
        return EntityModel.of(pago,
                linkTo(methodOn(PagoControllerV2.class).getItemPorId(pago.getId())).withSelfRel(),
                linkTo(methodOn(PagoControllerV2.class).getPagos()).withRel("pagos"));
    }
}
