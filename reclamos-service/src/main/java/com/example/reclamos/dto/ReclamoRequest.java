package com.example.reclamos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReclamoRequest {

    @NotBlank(message = "Motivo de reclamo es obligatorio")
    private String motivoReclamo;

    @NotBlank(message = "Descripcion de reclamo es obligatoriA")
    private String descripcionReclamo;

}



