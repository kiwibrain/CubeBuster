package duoc.clientes.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // Para esta clase usamos un usuario fijo.
    // Más adelante esto podría salir desde una base de datos.
    public boolean credencialesValidas(String username, String password) {
        return "cliente".equals(username) && "1234".equals(password);
    }
}
