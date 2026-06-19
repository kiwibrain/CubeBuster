package com.example.cupones;

import com.example.cupones.dto.PagoRequest;
import com.example.cupones.model.Pago;
import com.example.cupones.repository.PagoRepository;
import com.example.cupones.service.PagoService;
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
class PagoServiceTest {

	@Mock
	private PagoRepository pagoRepository;

	@Mock
	private ClienteClient clienteClient;

	@InjectMocks
	private PagoService pagoService;

	@Test
	void listarTodosTest() {
		// Given
		Pago pago1 = new Pago(1L, 10L, 5000, "EFECTIVO", LocalDate.now());
		Pago pago2 = new Pago(2L, 20L, 15000, "TARJETA", LocalDate.now());
		when(pagoRepository.findAll()).thenReturn(Arrays.asList(pago1, pago2));

		// When
		List<Pago> resultado = pagoService.getPagos();

		// Then
		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertEquals("EFECTIVO", resultado.get(0).getMetodoPago());
		assertEquals(15000, resultado.get(1).getMonto());
		verify(pagoRepository, times(1)).findAll();
	}

	@Test
	void savePagoExitosoTest() {
		// Given
		PagoRequest request = new PagoRequest(10L, 8000, "TARJETA");

		// Simulamos que el cliente existe
		Map<String, Object> mockCliente = new HashMap<>();
		mockCliente.put("id", 10L);
		mockCliente.put("nombre", "Juan Perez");
		when(clienteClient.obtenerClienteId(10L)).thenReturn(mockCliente);

		Pago pagoGuardado = new Pago(1L, 10L, 8000, "TARJETA", LocalDate.now());
		when(pagoRepository.save(any(Pago.class))).thenReturn(pagoGuardado);

		// When
		Pago resultado = pagoService.savePago(request);

		// Then
		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals(10L, resultado.getClienteId());
		assertEquals("TARJETA", resultado.getMetodoPago());
		assertNotNull(resultado.getFechaPago());
		verify(pagoRepository, times(1)).save(any(Pago.class));
	}

	@Test
	void savePagoClienteNoEncontradoTest() {
		// Given
		PagoRequest request = new PagoRequest(99L, 5000, "EFECTIVO");

		// Simulamos que el cliente no se encuentra
		when(clienteClient.obtenerClienteId(99L)).thenReturn(Collections.emptyMap());

		// When & Then
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			pagoService.savePago(request);
		});

		assertEquals("Cliente no encontrado, no se puede procesar el pago", exception.getMessage());
		verify(pagoRepository, never()).save(any(Pago.class));
	}

	@Test
	void getPagoPorIdExitosoTest() {
		// Given
		Pago pago = new Pago(1L, 10L, 12000, "TRANSFERENCIA", LocalDate.now());
		when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));

		// When
		Pago resultado = pagoService.getPago(1L);

		// Then
		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals("TRANSFERENCIA", resultado.getMetodoPago());
	}

	@Test
	void getPagoPorIdNoEncontradoTest() {
		// Given
		when(pagoRepository.findById(1L)).thenReturn(Optional.empty());

		// When & Then
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			pagoService.getPago(1L);
		});

		assertEquals("Pago no encontrado", exception.getMessage());
	}

	@Test
	void updatePagoTest() {
		// Given
		Pago pagoExistente = new Pago(1L, 10L, 5000, "EFECTIVO", LocalDate.now());
		when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoExistente));

		PagoRequest request = new PagoRequest(10L, 10000, "TARJETA");

		Pago pagoActualizado = new Pago(1L, 10L, 10000, "TARJETA", LocalDate.now());
		when(pagoRepository.save(any(Pago.class))).thenReturn(pagoActualizado);

		// When
		Pago resultado = pagoService.updatePago(1L, request);

		// Then
		assertNotNull(resultado);
		assertEquals(10000, resultado.getMonto());
		assertEquals("TARJETA", resultado.getMetodoPago());
		verify(pagoRepository, times(1)).save(pagoExistente);
	}
}