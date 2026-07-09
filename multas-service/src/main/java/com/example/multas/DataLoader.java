package com.example.multas;

import com.example.multas.model.Multa;
import com.example.multas.repository.MultaRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private MultaRepository multaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (multaRepository.count() == 0) {
            Faker faker = new Faker();

            System.out.println("Cargando datos falsos de multas...");

            for (int i = 0; i < 10; i++) {
                Multa multa = new Multa();

                multa.setClienteId((long) faker.number().numberBetween(1, 11));
                multa.setMotivo(faker.options().option("Devolución de Producto defectuoso", "Retraso en devolución", "No realizo devolución"));
                multa.setEstado(faker.options().option("PENDIENTE", "PAGADA", "ANULADA"));
                multa.setMonto(faker.number().numberBetween(2000, 10000));
                multa.setFechaEmision(LocalDate.now());

                multaRepository.save(multa);
            }
            System.out.println("multas creadas con exito");
        }

    }
}
