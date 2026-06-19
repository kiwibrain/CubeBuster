package com.example.cupones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultaRequest {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El monto de la multa es obligatorio")
    private Integer monto; // Cambiado de Long a Integer para coincidir con tu entidad

    @NotBlank(message = "El motivo de la multa es obligatorio")
    private String motivo; // Cambiado de Long a String para recibir el texto de la infracción
}