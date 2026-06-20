package com.example.sucursales.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sucursales")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreSucursal; // Ej: "CubeBuster Providencia"

    @Column(nullable = false)
    private String direccionSucursal; // Ej: "Av. Los Leones 123"

    private String telefonoSucursal; // Ej: "+56 9 1234 5678"

    private String horarioAtencion; // Ej: "Lunes a Domingo 10:00 - 21:00"
}