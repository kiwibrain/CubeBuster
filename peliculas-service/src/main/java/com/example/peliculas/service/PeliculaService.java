package com.example.peliculas.service;

import java.util.List;

import com.example.peliculas.dto.PeliculaRequest;
import com.example.peliculas.model.Pelicula;
import com.example.peliculas.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PeliculaService {
    @Autowired
    private PeliculaRepository peliculaRepository;

    // Metodo GET
    public List<Pelicula> getPeliculas(){
        return peliculaRepository.findAll();
    }

    // Metodo GET ID
    public Pelicula getPelicula(Long id){
        return peliculaRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Pelicula no encontrada"));
    }

    // Metodo POST
    public Pelicula savePelicula(PeliculaRequest request) {
        Pelicula nuevaPelicula = new Pelicula();
        nuevaPelicula.setNombrePelicula(request.getNombrePelicula());
        nuevaPelicula.setAnioPelicula(request.getAnioPelicula());
        nuevaPelicula.setCategoriaPelicula(request.getCategoriaPelicula());

        return peliculaRepository.save(nuevaPelicula);
    }

    // Metodo PUT
    public Pelicula updatePelicula(Long id, PeliculaRequest request) {
        Pelicula peliculaExistente = getPelicula(id);

        peliculaExistente.setNombrePelicula(request.getNombrePelicula());
        peliculaExistente.setAnioPelicula(request.getAnioPelicula());
        peliculaExistente.setPrecioPelicula(request.getPrecioPelicula());
        peliculaExistente.setCategoriaPelicula(request.getCategoriaPelicula());


        return peliculaRepository.save(peliculaExistente);
    }

    // Metodo DELETE
    public void deletePelicula(Long id) {
        Pelicula peliculaExistente = getPelicula(id);
        peliculaRepository.delete(peliculaExistente);
    }
}
