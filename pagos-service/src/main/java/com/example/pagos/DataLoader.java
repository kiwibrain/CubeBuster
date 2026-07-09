package com.example.pagos;

import com.example.pagos.model.Pago;
import com.example.pagos.repository.PagoRepository;
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
    private PagoRepository pagoRepository;

    @Override
    public void run (String ...args) throws Exception{
        if (pagoRepository.count() == 0) {
            Faker faker = new Faker();

            System.out.println("Cargando datos falsos de pagos...");
            for (int i = 0; i < 10; i++) {
                Pago pago = new Pago();

                pago.setClienteId((long) faker.number().numberBetween(1, 11));
                pago.setMonto(faker.number().numberBetween(2000, 100000));
                pago.setMetodoPago(faker.options().option("TRANSFERENCIA", "EFECTIVO", "TARJETA"));

                //fecha aleatoria ultimos 30 dias
                int diasAtras = faker.number().numberBetween(1, 30);
                pago.setFechaPago(LocalDate.now().minusDays(diasAtras));

                pagoRepository.save(pago);
            }
            System.out.println("pagos creados con exito");

        }
    }
}
