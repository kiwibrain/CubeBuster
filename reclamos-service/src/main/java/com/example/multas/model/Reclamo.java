package com.example.multas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "multas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reclamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String motivoReclamo;

    @Column(nullable = false)
    private String descripcionReclamo;

    @Column(nullable = false)
    private Long clienteId;
}
