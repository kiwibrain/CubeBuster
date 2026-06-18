package com.example.juegos;

import com.example.juegos.dto.JuegoRequest;
import com.example.juegos.exception.JuegoNoEncontradoException;
import com.example.juegos.model.Juego;
import com.example.juegos.repository.JuegoRepository;
import com.example.juegos.service.JuegoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JuegoServiceTest {

	@Mock
	private JuegoRepository juegoRepository; // Fingimos la base de datos

	@InjectMocks
	private JuegoService juegoService; // El servicio real que vamos a exprimir

	@Test
	void listarTodosTest() {
		// 1. Preparar el escenario (Given)
		Juego juego1 = new Juego(1L, "Super Metroid", 1994, 3990, "Aventura");
		Juego juego2 = new Juego(2L, "Turok: Dinosaur Hunter",1997, 3990, "Disparo");
		when(juegoRepository.findAll()).thenReturn(Arrays.asList(juego1, juego2));

		// 2. Ejecutar la acción (When)
		List<Juego> resultado = juegoService.getJuegos();

		// 3. Verificar el resultado (Then)
		assertEquals(2, resultado.size());
		assertEquals("Super Metroid", resultado.getFirst().getNombreJuego());
		verify(juegoRepository, times(1)).findAll();
	}

	@Test
	void buscarPorIdExitoTest() {
		// Given
		Juego juego = new Juego(1L, "Super Metroid", 1994, 3990, "Aventura");
		when(juegoRepository.findById(1L)).thenReturn(Optional.of(juego));

		// When
		Juego resultado = juegoService.getJuego(1L);

		// Then
		assertNotNull(resultado);
		assertEquals(1L, resultado.getIdJuego());
		assertEquals("Super Metroid", resultado.getNombreJuego());
		assertEquals(1994, resultado.getAnioJuego());
		assertEquals(3990, resultado.getPrecioJuego());
		assertEquals("Aventura", resultado.getCategoriaJuego());
	}

	@Test
	void buscarPorIdErrorTest() {
		// Given: Le decimos que cuando busque el ID 99, devuelva vacío
		when(juegoRepository.findById(99L)).thenReturn(Optional.empty());

		// When & Then: Esperamos que lance la excepción exacta
		assertThrows(JuegoNoEncontradoException.class, () -> {
			juegoService.getJuego(99L);
		});
	}


	@Test
	void actualizarTest() {
		// Given
		Juego clienteExistente = new Juego(1L, "Super Metroid", 1994, 3990, "Aventura");
		when(juegoRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));

		JuegoRequest request = new JuegoRequest();
		request.setNombreJuego("Super Metroid");
		request.setAnioJuego(1994);
		request.setPrecioJuego(3990);
		request.setCategoriaJuego("Aventura");

		Juego juegoActualizado = new Juego(1L, "Super Metroid", 1994, 3990, "Aventura");
		when(juegoRepository.save(any(Juego.class))).thenReturn(juegoActualizado);

		// When
		Juego resultado = juegoService.updateJuego(1L, request);

		// Then
		assertEquals("Super Metroid", resultado.getNombreJuego());
		assertEquals(1994, resultado.getAnioJuego());
		assertEquals(3990, resultado.getPrecioJuego());
		assertEquals("Aventura", resultado.getCategoriaJuego());
	}

	@Test
	void eliminarTest() {
		// Given
		Juego clienteExistente = new Juego(1L, "Super Metroid", 1994, 3990, "Aventura");
		when(juegoRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));

		// Para delete, no retornamos nada (void), solo queremos verificar que se llame
		doNothing().when(juegoRepository).delete(clienteExistente);

		// When
		juegoService.deleteJuego(1L);

		// Then
		verify(juegoRepository, times(1)).findById(1L);
		verify(juegoRepository, times(1)).delete(clienteExistente);
	}

}
