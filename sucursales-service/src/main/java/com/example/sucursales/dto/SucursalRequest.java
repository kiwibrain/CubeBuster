package com.example.sucursales.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SucursalRequest {

    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    private String nombreSucursal;

    @NotBlank(message = "La dirección de la sucursal es obligatoria")
    private String direccionSucursal;

    @NotBlank(message = "El teléfono de la sucursal es obligatorio")
    private String telefonoSucursal;

    @NotBlank(message = "El horario de atención es obligatorio")
    private String horarioAtencion;
}