package com.example.cupones;

import com.example.cupones.dto.MultaRequest;
import com.example.cupones.model.Multa;
import com.example.cupones.repository.MultaRepository;
import com.example.cupones.service.MultaService;
import com.example.cupones.webclient.ClienteClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MultaServiceTest {

	@Mock
	private MultaRepository multaRepository;

	@Mock
	private ClienteClient clienteClient;

	@InjectMocks
	private MultaService multaService;

	@Test
	void listarTodosTest() {
		// Given (Preparar escenario con casos reales de CubeBuster)
		Multa multa1 = new Multa(1L, 10L, 2500, "Atraso de 2 días en devolución de VHS", "PENDIENTE", LocalDate.now());
		Multa multa2 = new Multa(2L, 20L, 15000, "Juego de N64 devuelto con carcasa rota", "PENDIENTE", LocalDate.now());
		when(multaRepository.findAll()).thenReturn(Arrays.asList(multa1, multa2));

		// When (Ejecutar acción)
		List<Multa> resultado = multaService.getMultas();

		// Then (Verificar resultados)
		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertEquals("Atraso de 2 días en devolución de VHS", resultado.get(0).getMotivo());
		assertEquals(15000, resultado.get(1).getMonto());
		verify(multaRepository, times(1)).findAll();
	}

	@Test
	void saveMultasExitosoTest() {
		// Given
		MultaRequest request = new MultaRequest(10L, 5000, "Cinta de película masticada por videocasetera");

		// Simulamos que el cliente HTTP externo devuelve un mapa con datos (Cliente Existe)
		Map<String, Object> mockCliente = new HashMap<>();
		mockCliente.put("id", 10L);
		mockCliente.put("nombre", "Juan Perez");
		when(clienteClient.obtenerClienteId(10L)).thenReturn(mockCliente);

		// Simulamos la persistencia en base de datos
		Multa multaGuardada = new Multa(1L, 10L, 5000, "Cinta de película masticada por videocasetera", "PENDIENTE", LocalDate.now());
		when(multaRepository.save(any(Multa.class))).thenReturn(multaGuardada);

		// When
		Multa resultado = multaService.saveMultas(request);

		// Then
		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals(10L, resultado.getClienteId());
		assertEquals("PENDIENTE", resultado.getEstado());
		assertNotNull(resultado.getFechaEmision());
		verify(multaRepository, times(1)).save(any(Multa.class));
	}

	@Test
	void saveMultasClienteNoEncontradoTest() {
		// Given
		MultaRequest request = new MultaRequest(99L, 3000, "Atraso de 3 días en película de terror");

		// Simulamos que el cliente no se encuentra (WebClient devuelve null o vacío)
		when(clienteClient.obtenerClienteId(99L)).thenReturn(Collections.emptyMap());

		// When & Then
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			multaService.saveMultas(request);
		});

		assertEquals("Cliente no encontrado, no se puede agregar la multa", exception.getMessage());
		// Nos aseguramos que al fallar la validación, NUNCA se intente guardar en el repositorio
		verify(multaRepository, never()).save(any(Multa.class));
	}

	@Test
	void getMultaPorIdExitosoTest() {
		// Given
		Multa multa = new Multa(1L, 10L, 8000, "Disco de PS1 devuelto súper rayado", "PENDIENTE", LocalDate.now());
		when(multaRepository.findById(1L)).thenReturn(Optional.of(multa));

		// When
		Multa resultado = multaService.getMulta(1L);

		// Then
		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals("Disco de PS1 devuelto súper rayado", resultado.getMotivo());
	}

	@Test
	void getMultaPorIdNoEncontradoTest() {
		// Given
		when(multaRepository.findById(1L)).thenReturn(Optional.empty());

		// When & Then
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			multaService.getMulta(1L);
		});

		assertEquals("Multa no encontrada", exception.getMessage());
	}

	@Test
	void updateMultaTest() {
		// Given
		Multa multaExistente = new Multa(1L, 10L, 2000, "Atraso de 1 día", "PENDIENTE", LocalDate.now());
		when(multaRepository.findById(1L)).thenReturn(Optional.of(multaExistente));

		// El DTO de actualización solicita cambiar el motivo y el monto (porque pasaron más días)
		MultaRequest request = new MultaRequest(10L, 6000, "Atraso de 3 días");

		Multa multaActualizada = new Multa(1L, 10L, 6000, "Atraso de 3 días", "PENDIENTE", LocalDate.now());
		when(multaRepository.save(any(Multa.class))).thenReturn(multaActualizada);

		// When
		Multa resultado = multaService.updateMulta(1L, request);

		// Then
		assertNotNull(resultado);
		assertEquals(6000, resultado.getMonto());
		assertEquals("Atraso de 3 días", resultado.getMotivo());
		verify(multaRepository, times(1)).save(multaExistente);
	}
}