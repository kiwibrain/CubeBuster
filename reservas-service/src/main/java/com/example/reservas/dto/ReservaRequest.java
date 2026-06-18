package com.example.reservas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaRequest {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    // No son @NotNull porque la validación de "al menos uno" la hace el Service
    private Long idJuego;
    private Long idPelicula;

}