package duoc.clientes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import duoc.clientes.dto.AuthResponse;
import duoc.clientes.dto.ClienteRequest;
import duoc.clientes.dto.LoginRequest;
import duoc.clientes.model.Cliente;
import duoc.clientes.security.JwtService;
import duoc.clientes.service.AuthService;
import duoc.clientes.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

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
    public ResponseEntity<List<Cliente>> obtenerClientes() {
        List<Cliente> clientes = clienteService.listarTodos();
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.buscarPorId(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cliente> guardarCliente(@Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.status(201).body(clienteService.crearDesdeRequest(request));
    }
}