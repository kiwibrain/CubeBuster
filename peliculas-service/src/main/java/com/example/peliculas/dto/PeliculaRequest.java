package com.example.peliculas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PeliculaRequest {

    @NotBlank(message = "El nombre de la pelicula es obligatorio")
    private String nombrePelicula;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1888, message = "El año no puede ser menor a 1888")
    private Integer anioPelicula;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1000, message = "Precio de renta no puede ser menor a $1.000")
    private Integer precioPelicula;

    @NotBlank(message = "La categoria es obligatoria")
    private String categoriaPelicula;


}



