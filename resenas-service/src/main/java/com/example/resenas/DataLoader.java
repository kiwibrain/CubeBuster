package com.example.resenas;

import com.example.resenas.model.Resena;
import com.example.resenas.repository.ResenaRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ResenaRepository resenaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (resenaRepository.count() == 0) {
            Faker faker = new Faker();

            System.out.println("Cargando datos de reseñas...");
            for (int i = 0; i < 10; i++) {
                Resena resena = new Resena();

                //Decisión: 1=Pelicula, 2=Juego, 3=Ambos
                int tipoReserva = faker.number().numberBetween(1, 4);

                resena.setClienteId((long) faker.number().numberBetween(1, 11));

                if (tipoReserva == 1) { //peliculas
                    resena.setNombreItem(faker.movie().name());
                    resena.setIdPelicula((long) faker.number().numberBetween(1, 11));
                    resena.setNicknameCliente(faker.internet().username());
                    resena.setDescripcionResena(faker.lorem().sentence());

                } else if (tipoReserva == 2) { //juegos
                    resena.setNombreItem(faker.videoGame().title());
                    resena.setIdJuego((long) faker.number().numberBetween(1, 11));
                    resena.setNicknameCliente(faker.internet().username());
                    resena.setDescripcionResena(faker.lorem().sentence());

                } else { //ambos
                    String nombrePelicula = faker.movie().name();
                    String nombreJuego = faker.videoGame().title();

                    resena.setNombreItem(nombrePelicula + " y " + nombreJuego);

                    resena.setIdPelicula((long) faker.number().numberBetween(1, 11));
                    resena.setIdJuego((long) faker.number().numberBetween(1, 11));
                    resena.setNicknameCliente(faker.internet().username());
                    resena.setDescripcionResena(faker.lorem().sentence());
                }
                resenaRepository.save(resena);
            }
        }
        System.out.println("reseñas creadas con exito");
    }
}
