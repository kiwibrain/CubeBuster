package duoc.clientes.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import duoc.clientes.assemblers.ClienteModelAssembler;
import duoc.clientes.dto.AuthResponse;
import duoc.clientes.dto.ClienteRequest;
import duoc.clientes.dto.LoginRequest;
import duoc.clientes.model.Cliente;
import duoc.clientes.security.JwtService;
import duoc.clientes.service.AuthService;
import duoc.clientes.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/clientes")
public class ClienteControllerV2 {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteModelAssembler assembler;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (authService.credencialesValidas(request.getUsername(), request.getPassword())) {
            String token = jwtService.generarToken(request.getUsername());
            return ResponseEntity.ok(new AuthResponse(token, request.getUsername()));
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Cliente>>> obtenerClientes() {
        List<EntityModel<Cliente>> clientes = clienteService.listarTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        CollectionModel<EntityModel<Cliente>> collectionModel = CollectionModel.of(clientes,
                linkTo(methodOn(ClienteControllerV2.class).obtenerClientes()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> obtenerCliente(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.buscarPorId(id);
            return ResponseEntity.ok(assembler.toModel(cliente));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<Cliente>> guardarCliente(@Valid @RequestBody ClienteRequest request) {
        Cliente nuevoCliente = clienteService.crearDesdeRequest(request);

        return ResponseEntity
                .created(linkTo(methodOn(ClienteControllerV2.class).obtenerCliente(nuevoCliente.getId())).toUri())
                .body(assembler.toModel(nuevoCliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> actualizarCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {

        Cliente clienteActualizado = clienteService.actualizar(id, request);
        return ResponseEntity.ok(assembler.toModel(clienteActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}