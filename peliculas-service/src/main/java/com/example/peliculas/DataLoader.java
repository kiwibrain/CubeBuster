package com.example.peliculas;

import com.example.peliculas.model.Pelicula;
import com.example.peliculas.repository.PeliculaRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Override
    public void run (String... args) throws Exception {
        if (peliculaRepository.count() == 0){
            Faker faker = new Faker();

            System.out.println("Cargando datos falsos de peliculas...");

            for (int i = 0; i < 10; i++){
                Pelicula pelicula = new Pelicula();

                pelicula.setNombrePelicula(faker.movie().name());
                pelicula.setAnioPelicula(faker.number().numberBetween(1980,2025));
                pelicula.setPrecioPelicula(faker.number().numberBetween(2000,5000));
                pelicula.setCategoriaPelicula(faker.options().option("Terror", "Comedia", "Fantasia", "Ciencia Ficción"));

                peliculaRepository.save(pelicula);
            }
            System.out.println("Peliculas creadas con exito");
        }
    }
}
