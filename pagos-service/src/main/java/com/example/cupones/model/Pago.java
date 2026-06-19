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
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;

    private int monto;

    private String metodoPago; // "EFECTIVO", "TARJETA", "TRANSFERENCIA"

    private LocalDate fechaPago; // Se registra cuándo se hizo el pago
}