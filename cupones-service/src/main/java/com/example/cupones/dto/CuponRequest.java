package com.example.cupones.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuponRequest {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotBlank(message = "El código del cupón es obligatorio")
    private String codigo;

    @NotNull(message = "El porcentaje de descuento es obligatorio")
    @Min(value = 1, message = "El descuento debe ser mayor a 0")
    @Max(value = 100, message = "El descuento no puede superar el 100%")
    private Integer porcentajeDescuento;

    @NotNull(message = "La fecha de expiración es obligatoria")
    private LocalDate fechaExpiracion;
}