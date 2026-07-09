package com.example.sucursales;

import com.example.sucursales.model.Sucursal;
import com.example.sucursales.repository.SucursalRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public void run(String... args) throws Exception {
        if (sucursalRepository.count() == 0) {
            Faker faker = new Faker();

            System.out.println("Cargando datos de sucursales...");
            for (int i = 0; i < 10; i++) {
                Sucursal sucursal = new Sucursal();

                sucursal.setDireccionSucursal(faker.address().fullAddress());
                sucursal.setNombreSucursal(faker.location().building());
                sucursal.setTelefonoSucursal(faker.phoneNumber().phoneNumber());
                sucursal.setHorarioAtencion(faker.options().option(
                        "Lunes a Viernes: 09:00 - 18:00",
                        "Lunes a Viernes: 08:00 - 20:00",
                        "24 Horas",
                        "Solo fines de semana: 10:00 - 16:00"));

                sucursalRepository.save(sucursal);
            }
            System.out.println("sucursales creadas correctamente...");

        }
    }
}
