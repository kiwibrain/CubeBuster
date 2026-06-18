package com.example.juegos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class JuegoRequest {

    @NotBlank(message = "El nombre del juego es obligatorio")
    private String nombreJuego;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1958, message = "El año no puede ser menor a 1958")
    private Integer anioJuego;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1000, message = "Precio de renta no puede ser menor a $1.000")
    private Integer precioJuego;

    @NotBlank(message = "La categoria es obligatoria")
    private String categoriaJuego;


}



