package com.example.cupones.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "cupones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId; // El cliente dueño del cupón

    private String codigo; // Ej: "RETRO20", "MATRIX50"

    private int porcentajeDescuento; // Ej: 15 (para un 15%)

    private LocalDate fechaExpiracion; // Hasta cuándo es válido
}