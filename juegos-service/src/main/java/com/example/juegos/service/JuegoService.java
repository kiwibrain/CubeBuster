package com.example.juegos.service;

import java.util.List;

import com.example.juegos.dto.JuegoRequest;
import com.example.juegos.model.Juego;
import com.example.juegos.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class JuegoService {
    @Autowired
    private JuegoRepository juegoRepository;

    // Metodo GET
    public List<Juego> getJuegos(){
        return juegoRepository.findAll();
    }

    // Metodo GET ID
    public Juego getJuego(Long id){
        return juegoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Pelicula no encontrada"));
    }

    // Metodo POST
    public Juego saveJuego(JuegoRequest request) {
        Juego nuevaPelicula = new Juego();
        nuevaPelicula.setNombreJuego(request.getNombreJuego());
        nuevaPelicula.setAnioJuego(request.getAnioJuego());
        nuevaPelicula.setCategoriaJuego(request.getCategoriaJuego());

        return juegoRepository.save(nuevaPelicula);
    }

    // Metodo PUT
    public Juego updateJuego(Long id, JuegoRequest request) {
        Juego peliculaExistente = getJuego(id);

        peliculaExistente.setNombreJuego(request.getNombreJuego());
        peliculaExistente.setAnioJuego(request.getAnioJuego());
        peliculaExistente.setPrecioJuego(request.getPrecioJuego());
        peliculaExistente.setCategoriaJuego(request.getCategoriaJuego());


        return juegoRepository.save(peliculaExistente);
    }

    // Metodo DELETE
    public void deleteJuego(Long id) {
        Juego peliculaExistente = getJuego(id);
        juegoRepository.delete(peliculaExistente);
    }
}
