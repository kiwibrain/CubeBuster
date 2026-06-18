package duoc.clientes.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import duoc.clientes.dto.ClienteRequest;
import duoc.clientes.exception.ClienteNoEncontradoException;
import duoc.clientes.model.Cliente;
import duoc.clientes.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        log.info("Listando todos los clientes");
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        log.info("Buscando cliente con id: {}", id);
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNoEncontradoException("No se encontró el cliente con id: " + id));
    }

    public Cliente crearDesdeRequest(ClienteRequest request) {
        log.info("Creando cliente con email: {}", request.getEmail());

        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setEmail(request.getEmail());

        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Long id, ClienteRequest request) {
        log.info("Actualizando cliente con id: {}", id);

        Cliente cliente = buscarPorId(id);
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setEmail(request.getEmail());

        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
        log.info("Eliminando cliente con id: {}", id);
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }

    public void simularError() {
        log.error("Se ejecutó el método para simular un error interno");
        throw new RuntimeException("Error simulado para pruebas");
    }
}