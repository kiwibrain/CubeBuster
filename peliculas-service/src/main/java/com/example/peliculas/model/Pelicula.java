package com.example.peliculas.model;

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
@Table(name = "peliculas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPelicula;

    @Column(nullable = false)
    private String nombrePelicula;

    @Column(nullable = false)
    private int anioPelicula;

    @Column(nullable = false)
    private int precioPelicula;

    @Column(nullable = false)
    private String categoriaPelicula;

}
