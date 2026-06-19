package com.example.resenas;

import com.example.resenas.dto.ReclamoRequest;
import com.example.resenas.exception.ReclamoNoEncontradoException;
import com.example.resenas.model.Reclamo;
import com.example.resenas.repository.ReclamoRepository;
import com.example.resenas.service.ReclamoService;
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
	private ReclamoRepository reclamoRepository; // Fingimos la base de datos

	@InjectMocks
	private ReclamoService reclamoService; // El servicio real que vamos a exprimir

	@Test
	void listarTodosTest() {
		// 1. Preparar el escenario (Given)
		Reclamo reclamo1 = new Reclamo(1L, "Disco rayado","Disco reservado esta en mal estado", 1L);
		Reclamo reclamo2 = new Reclamo(2L, "Mal atencion","Queja por mal trato por parte del personal de la tienda", 2L);
		when(reclamoRepository.findAll()).thenReturn(Arrays.asList(reclamo1, reclamo2));

		// 2. Ejecutar la acción (When)
		List<Reclamo> resultado = reclamoService.getReclamos();

		// 3. Verificar el resultado (Then)
		assertEquals(2, resultado.size());
		assertEquals("Disco rayado", resultado.getFirst().getMotivoReclamo());
		verify(reclamoRepository, times(1)).findAll();
	}

	@Test
	void buscarPorIdExitoTest() {
		// Given
		Reclamo reclamo = new Reclamo(1L, "Disco rayado","Disco reservado esta en mal estado", 1L);
		when(reclamoRepository.findById(1L)).thenReturn(Optional.of(reclamo));

		// When
		Reclamo resultado = reclamoService.getReclamos(1L);

		// Then
		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals("Super Metroid", resultado.getMotivoReclamo());
		assertEquals("Aventura", resultado.getDescripcionReclamo());
		assertEquals(1L, resultado.getClienteId());
	}

	@Test
	void buscarPorIdErrorTest() {
		// Given: Le decimos que cuando busque el ID 99, devuelva vacío
		when(reclamoRepository.findById(99L)).thenReturn(Optional.empty());

		// When & Then: Esperamos que lance la excepción exacta
		assertThrows(ReclamoNoEncontradoException.class, () -> {
			reclamoService.getReclamos(99L);
		});
	}


	@Test
	void actualizarTest() {
		// Given
		Reclamo reclamoExistente = new Reclamo(1L, "Disco rayado","Disco reservado esta en mal estado", 1L);
		when(reclamoRepository.findById(1L)).thenReturn(Optional.of(reclamoExistente));

		ReclamoRequest request = new ReclamoRequest();
		request.setMotivoReclamo("Disco rayado");
		request.setDescripcionReclamo("Disco reservado esta en mal estado");

		Reclamo reclamoActualizado = new Reclamo(1L, "Disco rayado","Disco reservado esta en mal estado", 1L);
		when(reclamoRepository.save(any(Reclamo.class))).thenReturn(reclamoActualizado);

		// When
		Reclamo resultado = reclamoService.updateReclamo(1L, request);

		// Then
		assertEquals("Disco rayado", resultado.getMotivoReclamo());
		assertEquals("Disco reservado esta en mal estado", resultado.getDescripcionReclamo());
		assertEquals(1L, resultado.getClienteId());
	}

	@Test
	void eliminarTest() {
		// Given
		Reclamo reclamoExistente = new Reclamo(1L, "Disco rayado","Disco reservado esta en mal estado", 1L);
		when(reclamoRepository.findById(1L)).thenReturn(Optional.of(reclamoExistente));

		// Para delete, no retornamos nada (void), solo queremos verificar que se llame
		doNothing().when(reclamoRepository).delete(reclamoExistente);

		// When
		reclamoService.deleteReclamo(1L);

		// Then
		verify(reclamoRepository, times(1)).findById(1L);
		verify(reclamoRepository, times(1)).delete(reclamoExistente);
	}

}
