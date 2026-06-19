package com.example.pagos.model;

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
@Table(name = "pagos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;

    private Long idJuego;

    private Long idPelicula;

    //nombre del juego/pelicula extraido del ID
    private String nombreItem;

    @Column(nullable = false)
    private String descripcionResena;

    //permite al cliente poner un nickname para la reseña si no desea poner su nombre
    private String nicknameCliente;

}
