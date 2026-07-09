package com.example.reclamos;

import com.example.reclamos.model.Reclamo;
import com.example.reclamos.repository.ReclamoRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private ReclamoRepository reclamoRepository;

    @Override
    public void run (String... args) throws Exception {
        if (reclamoRepository.count() == 0) {
            Faker faker = new Faker();

            System.out.println("Cargando datos falsos de reclamos...");

            for (int i = 0; i < 10; i++) {
                Reclamo reclamo = new Reclamo();

                long randomClienteId = faker.number().numberBetween(1L, 11L);
                reclamo.setClienteId(randomClienteId);

                reclamo.setMotivoReclamo(faker.options().option("Producto defectuoso", "Mala atención", "Retraso en entrega", "Cobro Indebido"));
                reclamo.setDescripcionReclamo(faker.lorem().sentence());

                reclamoRepository.save(reclamo);
            }
            System.out.println("Reclamos creados con exito");
        }
    }
}
