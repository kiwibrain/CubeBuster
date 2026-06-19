package com.example.multas;

import com.example.multas.dto.ResenaRequest;
import com.example.multas.model.Resena;
import com.example.multas.repository.ResenaRepository;
import com.example.multas.service.ResenaService;
import com.example.multas.webclient.ClienteClient;
import com.example.multas.webclient.JuegoClient;
import com.example.multas.webclient.PeliculaClient;
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
class ResenaServiceTest {

	@Mock
	private ResenaRepository resenaRepository;

	@Mock
	private ClienteClient clienteClient;

	@Mock
	private JuegoClient juegoClient;

	@Mock
	private PeliculaClient peliculaClient;

	@InjectMocks
	private ResenaService resenaService;

	@Test
	void listarTodosTest() {
		// 1. Preparar el escenario (Given)
		Resena resena1 = new Resena(1L, 1L, 5L, null, "Zelda", "Juegazo increíble", "Gamer123");
		Resena resena2 = new Resena(2L, 2L, null, 8L, "Matrix", "Me voló la cabeza", "Cinefilo99");
		when(resenaRepository.findAll()).thenReturn(Arrays.asList(resena1, resena2));

		// 2. Ejecutar la acción (When)
		List<Resena> resultado = resenaService.getResenas();

		// 3. Verificar (Then)
		assertEquals(2, resultado.size());
		assertEquals("Zelda", resultado.get(0).getNombreItem());
	}

	@Test
	void saveResenaJuegoTest() {
		// Given
		ResenaRequest request = new ResenaRequest();
		request.setClienteId(1L);
		request.setIdJuego(5L);
		request.setDescripcionResena("Muy bueno");
		request.setNicknameCliente("Anonimo");

		// Simulamos que el cliente existe
		Map<String, Object> mockCliente = new HashMap<>();
		mockCliente.put("nombre", "Juan Perez");
		when(clienteClient.obtenerClienteId(1L)).thenReturn(mockCliente);

		// Simulamos que el juego existe y nos devuelve su título
		Map<String, Object> mockJuego = new HashMap<>();
		mockJuego.put("titulo", "Super Mario");
		when(juegoClient.obtenerJuegoId(5L)).thenReturn(mockJuego);

		Resena resenaGuardada = new Resena(1L, 1L, 5L, null, "Super Mario", "Muy bueno", "Anonimo");
		when(resenaRepository.save(any(Resena.class))).thenReturn(resenaGuardada);

		// When
		Resena resultado = resenaService.saveResena(request);

		// Then
		assertNotNull(resultado);
		assertEquals("Super Mario", resultado.getNombreItem());
		assertEquals("Muy bueno", resultado.getDescripcionResena());
	}

	@Test
	void getResenaNoEncontradaTest() {
		// Given
		when(resenaRepository.findById(99L)).thenReturn(Optional.empty());

		// When & Then
		assertThrows(RuntimeException.class, () -> {
			resenaService.getResena(99L);
		});
	}
}