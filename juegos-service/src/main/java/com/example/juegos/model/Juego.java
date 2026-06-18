package com.example.juegos.model;

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
@Table(name = "juegos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Juego {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJuego;

    @Column(nullable = false)
    private String nombreJuego;

    @Column(nullable = false)
    private int anioJuego;

    @Column(nullable = false)
    private int precioJuego;

    @Column(nullable = false)
    private String categoriaJuego;

}
