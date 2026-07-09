package com.example.cupones;

import com.example.cupones.model.Cupon;
import com.example.cupones.repository.CuponRepository;
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
    private CuponRepository cuponRepository;

    @Override
    public void run (String... args) throws Exception {
        if (cuponRepository.count() == 0) {
            Faker faker = new Faker();

            System.out.println("Cargando datos falsos de cupones...");

            for (int i = 0; i < 10; i++) {
                Cupon cupon = new Cupon();

                cupon.setClienteId((long) faker.number().numberBetween(1,11));

                int descuento = faker.options().option(10, 15, 20, 25, 30, 40, 50);
                cupon.setPorcentajeDescuento(descuento);

                String palabraFalsa = faker.lorem().word().toUpperCase();
                cupon.setCodigo(palabraFalsa + descuento);

                int diasFuturos = faker.number().numberBetween(1,60);
                cupon.setFechaExpiracion(LocalDate.now().plusDays(diasFuturos));

                cuponRepository.save(cupon);
            }
            System.out.println("cupones creados con exito");
        }
    }
}
