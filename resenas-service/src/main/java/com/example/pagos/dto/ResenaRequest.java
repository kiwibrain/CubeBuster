package com.example.pagos.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResenaRequest {

    private Long clienteId;
    private Long idJuego;
    private Long idPelicula;

    @NotBlank(message = "La descripción de la reseña es obligatoria")
    private String descripcionResena;

    private String nicknameCliente;
}