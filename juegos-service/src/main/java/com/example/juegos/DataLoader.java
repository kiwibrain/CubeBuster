package com.example.juegos;

import com.example.juegos.model.Juego;
import com.example.juegos.repository.JuegoRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private JuegoRepository juegoRepository;

    @Override
    public void run (String... args) throws Exception {
        if (juegoRepository.count() == 0){
            Faker faker = new Faker();

            System.out.println("Cargando datos falsos de juegos...");

            for (int i = 0; i < 10; i++){
                Juego juego = new Juego();

                juego.setNombreJuego(faker.videoGame().title());
                juego.setAnioJuego(faker.number().numberBetween(1980,2025));
                juego.setPrecioJuego(faker.number().numberBetween(2000,5000));
                juego.setCategoriaJuego(faker.options().option("Terror", "Acción", "Aventura", "FPS"));

                juegoRepository.save(juego);
            }
            System.out.println("Juegos creados con exito");
        }
    }
}
