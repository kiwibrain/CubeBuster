package com.example.reservas.service;

import java.util.List;
import java.util.Map;

import com.example.reservas.dto.ReservaRequest;
import com.example.reservas.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reservas.repository.ReservaRepository;
import com.example.reservas.webclient.ClienteClient;
import com.example.reservas.webclient.JuegoClient;
import com.example.reservas.webclient.PeliculaClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private ClienteClient clienteClient;
    @Autowired
    private JuegoClient juegoClient;
    @Autowired
    private PeliculaClient peliculaClient;

    public List<Reserva> getReservas() {
        return reservaRepository.findAll();
    }

    public Reserva getReserva(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }

    public Reserva guardarReserva(ReservaRequest request) {
        Reserva nuevaReserva = new Reserva();
        return procesarYGuardar(nuevaReserva, request);
    }

    public Reserva updateReserva(Long id, ReservaRequest request) {
        Reserva reservaExistente = getReserva(id);
        return procesarYGuardar(reservaExistente, request);
    }

    public void deleteReserva(Long id) {
        Reserva reservaExistente = getReserva(id);
        reservaRepository.delete(reservaExistente);
    }

    // MÉTODO PRIVADO: para validacion de items en reserva
    private Reserva procesarYGuardar(Reserva reserva, ReservaRequest request) {
        // 1. Validar Cliente
        Map<String, Object> cliente = clienteClient.obtenerClienteId(request.getClienteId());
        if (cliente == null || cliente.isEmpty()) {
            throw new RuntimeException("Cliente no encontrado, no se puede realizar la reserva.");
        }

        StringBuilder detalleReserva = new StringBuilder();
        boolean tieneContenido = false;

        // 2. Procesar Película
        if (request.getIdPelicula() != null && request.getIdPelicula() > 0) {
            Map<String, Object> pelicula = peliculaClient.obtenerPeliculaId(request.getIdPelicula());
            if (pelicula != null && !pelicula.isEmpty()) {
                String nombrePeli = (String) pelicula.get("nombrePelicula");
                detalleReserva.append("Cine: ").append(nombrePeli);
                tieneContenido = true;
            } else {
                throw new RuntimeException("La película solicitada no existe.");
            }
        }

        // 3. Procesar Juego
        if (request.getIdJuego() != null && request.getIdJuego() > 0) {
            Map<String, Object> juego = juegoClient.obtenerJuegoId(request.getIdJuego());
            if (juego != null && !juego.isEmpty()) {
                if (tieneContenido) detalleReserva.append(" + ");
                String nombreJuego = (String) juego.get("nombreJuego");
                detalleReserva.append("Game: ").append(nombreJuego);
                tieneContenido = true;
            } else {
                throw new RuntimeException("El juego solicitado no existe.");
            }
        }

        // 4. Validación final
        if (!tieneContenido) {
            throw new RuntimeException("Debe seleccionar al menos una película o un juego.");
        }

        // 5. Traspasar datos del DTO a la Entidad y guardar
        reserva.setClienteId(request.getClienteId());
        reserva.setIdPelicula(request.getIdPelicula());
        reserva.setIdJuego(request.getIdJuego());
        reserva.setNombre(detalleReserva.toString());

        return reservaRepository.save(reserva);
    }
}