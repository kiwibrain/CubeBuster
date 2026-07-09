package com.example.reservas;

import com.example.reservas.model.Reserva;
import com.example.reservas.repository.ReservaRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public void run (String... args) throws Exception {
        if (reservaRepository.count() == 0) {
            Faker faker = new Faker();

            System.out.println("Cargando datos falsos de reservas...");

            for (int i = 0; i < 10; i++) {
                Reserva reserva = new Reserva();

                reserva.setClienteId((long) faker.number().numberBetween(1,11));

                //Decisión: 1=Pelicula, 2=Juego, 3=Ambos
                int tipoReserva = faker.number().numberBetween(1, 4);

                if (tipoReserva == 1) { //peliculas
                    reserva.setNombre(faker.movie().name());
                    reserva.setIdPelicula((long)faker.number().numberBetween(1,11));
                } else if (tipoReserva == 2) { //juegos
                    reserva.setNombre(faker.videoGame().title());
                    reserva.setIdJuego((long)faker.number().numberBetween(1,11));
                } else { //ambos
                    String nombrePelicula = faker.movie().name();
                    String nombreJuego = faker.videoGame().title();

                    reserva.setNombre(nombrePelicula + " y " + nombreJuego);

                    reserva.setIdPelicula((long)faker.number().numberBetween(1,11));
                    reserva.setIdJuego((long)faker.number().numberBetween(1,11));
                }


                reservaRepository.save(reserva);
            }
            System.out.println("Reservas creadas con exito");
        }
    }
}
