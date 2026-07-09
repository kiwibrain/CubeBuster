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
                .orElseThrow(()-> new RuntimeException("Juego no encontrada"));
    }

    // Metodo POST
    public Juego saveJuego(JuegoRequest request) {
        Juego nuevoJuego = new Juego();
        nuevoJuego.setNombreJuego(request.getNombreJuego());
        nuevoJuego.setAnioJuego(request.getAnioJuego());
        nuevoJuego.setPrecioJuego(request.getPrecioJuego());
        nuevoJuego.setCategoriaJuego(request.getCategoriaJuego());

        return juegoRepository.save(nuevoJuego);
    }

    // Metodo PUT
    public Juego updateJuego(Long id, JuegoRequest request) {
        Juego juegoExistente = getJuego(id);

        juegoExistente.setNombreJuego(request.getNombreJuego());
        juegoExistente.setAnioJuego(request.getAnioJuego());
        juegoExistente.setPrecioJuego(request.getPrecioJuego());
        juegoExistente.setCategoriaJuego(request.getCategoriaJuego());


        return juegoRepository.save(juegoExistente);
    }

    // Metodo DELETE
    public void deleteJuego(Long id) {
        Juego peliculaExistente = getJuego(id);
        juegoRepository.delete(peliculaExistente);
    }
}
