package com.example.peliculas;

import com.example.peliculas.dto.PeliculaRequest;
import com.example.peliculas.exception.PeliculaNoEncontradaException;
import com.example.peliculas.model.Pelicula;
import com.example.peliculas.repository.PeliculaRepository;
import com.example.peliculas.service.PeliculaService;
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
class PeliculaServiceTest {

	@Mock
	private PeliculaRepository peliculaRepository; // Fingimos la base de datos

	@InjectMocks
	private PeliculaService peliculaService; // El servicio real que vamos a exprimir

	@Test
	void listarTodosTest() {
		// 1. Preparar el escenario (Given)
		Pelicula pelicula1 = new Pelicula(1L, "Blade Runner", 1982, 6990, "Ciencia Ficción");
		Pelicula pelicula2 = new Pelicula(2L, "WALL-E",2008, 3990, "Animación");
		when(peliculaRepository.findAll()).thenReturn(Arrays.asList(pelicula1, pelicula2));

		// 2. Ejecutar la acción (When)
		List<Pelicula> resultado = peliculaService.getPeliculas();

		// 3. Verificar el resultado (Then)
		assertEquals(2, resultado.size());
		assertEquals("Blade Runner", resultado.getFirst().getNombrePelicula());
		verify(peliculaRepository, times(1)).findAll();
	}

	@Test
	void buscarPorIdExitoTest() {
		// Given
		Pelicula pelicula = new Pelicula(1L, "Blade Runner", 1982, 6990, "Ciencia Ficción");
		when(peliculaRepository.findById(1L)).thenReturn(Optional.of(pelicula));

		// When
		Pelicula resultado = peliculaService.getPelicula(1L);

		// Then
		assertNotNull(resultado);
		assertEquals(1L, resultado.getIdPelicula());
		assertEquals("Blade Runner", resultado.getNombrePelicula());
		assertEquals(1982, resultado.getAnioPelicula());
		assertEquals(6990, resultado.getPrecioPelicula());
		assertEquals("Ciencia Ficción", resultado.getCategoriaPelicula());
	}

	@Test
	void buscarPorIdErrorTest() {
		// Given: Le decimos que cuando busque el ID 99, devuelva vacío
		when(peliculaRepository.findById(99L)).thenReturn(Optional.empty());

		// When & Then: Esperamos que lance la excepción exacta
		assertThrows(PeliculaNoEncontradaException.class, () -> {
			peliculaService.getPelicula(99L);
		});
	}


	@Test
	void actualizarTest() {
		// Given
		Pelicula clienteExistente = new Pelicula(1L, "Blade Runner", 1982, 6990,"Ciencia Ficción");
		when(peliculaRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));

		PeliculaRequest request = new PeliculaRequest();
		request.setNombrePelicula("Blade Runner");
		request.setAnioPelicula(1982);
		request.setPrecioPelicula(6990);
		request.setCategoriaPelicula("Ciencia Ficción");

		Pelicula peliculaActualizado = new Pelicula(1L, "Blade Runner", 1982, 6990,"Ciencia Ficción");
		when(peliculaRepository.save(any(Pelicula.class))).thenReturn(peliculaActualizado);

		// When
		Pelicula resultado = peliculaService.updatePelicula(1L, request);

		// Then
		assertEquals("Blade Runner", resultado.getNombrePelicula());
		assertEquals(1982, resultado.getAnioPelicula());
		assertEquals(6990, resultado.getPrecioPelicula());
		assertEquals("Ciencia Ficción", resultado.getCategoriaPelicula());
	}

	@Test
	void eliminarTest() {
		// Given
		Pelicula clienteExistente = new Pelicula(1L, "Blade Runner", 1982, 6990,"Ciencia Ficción");
		when(peliculaRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));

		// Para delete, no retornamos nada (void), solo queremos verificar que se llame
		doNothing().when(peliculaRepository).delete(clienteExistente);

		// When
		peliculaService.deletePelicula(1L);

		// Then
		verify(peliculaRepository, times(1)).findById(1L);
		verify(peliculaRepository, times(1)).delete(clienteExistente);
	}

}
