package com.example.reservas; // Asegúrate de que el paquete sea correcto

import com.example.reservas.dto.ReservaRequest;
import com.example.reservas.model.Reserva;
import com.example.reservas.repository.ReservaRepository;
import com.example.reservas.service.ReservaService;
import com.example.reservas.webclient.ClienteClient;
import com.example.reservas.webclient.JuegoClient;
import com.example.reservas.webclient.PeliculaClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

	@Mock
	private ReservaRepository reservaRepository;

	// AHORA TENEMOS QUE MOCKEAR LOS 3 CLIENTES EXTERNOS
	@Mock
	private ClienteClient clienteClient;
	@Mock
	private JuegoClient juegoClient;
	@Mock
	private PeliculaClient peliculaClient;

	@InjectMocks
	private ReservaService reservaService;

	// ==========================================
	// TESTS BÁSICOS (Misma lógica que los otros)
	// ==========================================

	@Test
	void getReservasTest() {
		Reserva r1 = new Reserva(1L, "Cine: Blade Runner", 1L, null, 1L);
		Reserva r2 = new Reserva(2L, "Game: Super Metroid", 2L, 1L, null);
		when(reservaRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

		List<Reserva> resultado = reservaService.getReservas();

		assertEquals(2, resultado.size());
		verify(reservaRepository, times(1)).findAll();
	}

	@Test
	void getReservaExitoTest() {
		Reserva reserva = new Reserva(1L, "Cine: Blade Runner", 1L, null, 1L);
		when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

		Reserva resultado = reservaService.getReserva(1L);

		assertNotNull(resultado);
		assertEquals("Cine: Blade Runner", resultado.getNombre());
	}

	// ==========================================
	// TESTS AVANZADOS (Mockeando WebClients)
	// ==========================================

	@Test
	void guardarReservaSoloPeliculaTest() {
		// 1. GIVEN (PREPARAR ESCENARIO)
		ReservaRequest request = new ReservaRequest(1L, null, 1L); // Cliente 1, Juego Null, Peli 1

		// Fingimos que el microservicio de Clientes responde que existe
		Map<String, Object> mockCliente = new HashMap<>();
		mockCliente.put("id", 1L);
		when(clienteClient.obtenerClienteId(1L)).thenReturn(mockCliente);

		// Fingimos que el microservicio de Películas responde que existe y se llama "Blade Runner"
		Map<String, Object> mockPeli = new HashMap<>();
		mockPeli.put("nombrePelicula", "Blade Runner");
		when(peliculaClient.obtenerPeliculaId(1L)).thenReturn(mockPeli);

		// Fingimos el guardado en BD
		Reserva reservaGuardada = new Reserva(1L, "Cine: Blade Runner", 1L, null, 1L);
		when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaGuardada);

		// 2. WHEN (EJECUTAR ACCIÓN)
		Reserva resultado = reservaService.guardarReserva(request);

		// 3. THEN (VERIFICAR)
		assertNotNull(resultado);
		assertEquals("Cine: Blade Runner", resultado.getNombre());
		verify(clienteClient, times(1)).obtenerClienteId(1L);
		verify(peliculaClient, times(1)).obtenerPeliculaId(1L);
		verify(juegoClient, never()).obtenerJuegoId(anyLong()); // Nunca debió llamar a Juegos
	}

	@Test
	void guardarReservaErrorClienteNoExisteTest() {
		// GIVEN: Un request cualquiera
		ReservaRequest request = new ReservaRequest(99L, 1L, 1L);

		// Fingimos que el microservicio de Clientes devuelve nulo (no lo encontró)
		when(clienteClient.obtenerClienteId(99L)).thenReturn(null);

		// WHEN & THEN: Esperamos que lance el error exacto que programaste en tu Service
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			reservaService.guardarReserva(request);
		});

		assertEquals("Cliente no encontrado, no se puede realizar la reserva.", exception.getMessage());

		// Verificamos que al fallar el cliente, el proceso se cortó y nunca llamó a Películas ni Juegos
		verify(peliculaClient, never()).obtenerPeliculaId(anyLong());
		verify(juegoClient, never()).obtenerJuegoId(anyLong());
	}
}