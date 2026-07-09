package duoc.clientes;

import duoc.clientes.model.Cliente;
import duoc.clientes.repository.ClienteRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void run (String... args) throws Exception {
        if (clienteRepository.count() == 0){
            Faker faker = new Faker();

            System.out.println("Cargando datos falsos de clientes...");

            for (int i = 0; i < 10; i++){
                Cliente cliente = new Cliente();

                cliente.setNombre(faker.name().firstName());
                cliente.setApellido(faker.name().lastName());
                cliente.setEmail(faker.internet().emailAddress());

                clienteRepository.save(cliente);
            }
            System.out.println("Clientes creados con exito");
        }
    }
}
