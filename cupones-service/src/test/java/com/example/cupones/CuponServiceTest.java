package com.example.cupones;

import com.example.cupones.dto.CuponRequest;
import com.example.cupones.model.Cupon;
import com.example.cupones.repository.CuponRepository;
import com.example.cupones.service.CuponService;
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
class CuponServiceTest {

	@Mock
	private CuponRepository cuponRepository;

	@Mock
	private ClienteClient clienteClient;

	@InjectMocks
	private CuponService cuponService;

	@Test
	void listarTodosTest() {
		// Given
		Cupon cupon1 = new Cupon(1L, 10L, "RETRO20", 20, LocalDate.now().plusDays(30));
		Cupon cupon2 = new Cupon(2L, 20L, "NOSTALGIA10", 10, LocalDate.now().plusDays(15));
		when(cuponRepository.findAll()).thenReturn(Arrays.asList(cupon1, cupon2));

		// When
		List<Cupon> resultado = cuponService.getCupones();

		// Then
		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertEquals("RETRO20", resultado.get(0).getCodigo());
		assertEquals(10, resultado.get(1).getPorcentajeDescuento());
		verify(cuponRepository, times(1)).findAll();
	}

	@Test
	void saveCuponExitosoTest() {
		// Given
		CuponRequest request = new CuponRequest(10L, "FIDELIDAD50", 50, LocalDate.now().plusDays(60));

		// Simulamos que el cliente existe
		Map<String, Object> mockCliente = new HashMap<>();
		mockCliente.put("id", 10L);
		mockCliente.put("nombre", "Juan Perez");
		when(clienteClient.obtenerClienteId(10L)).thenReturn(mockCliente);

		Cupon cuponGuardado = new Cupon(1L, 10L, "FIDELIDAD50", 50, LocalDate.now().plusDays(60));
		when(cuponRepository.save(any(Cupon.class))).thenReturn(cuponGuardado);

		// When
		Cupon resultado = cuponService.saveCupon(request);

		// Then
		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals(10L, resultado.getClienteId());
		assertEquals("FIDELIDAD50", resultado.getCodigo());
		verify(cuponRepository, times(1)).save(any(Cupon.class));
	}

	@Test
	void saveCuponClienteNoEncontradoTest() {
		// Given
		CuponRequest request = new CuponRequest(99L, "FALLA10", 10, LocalDate.now());

		// Simulamos que el cliente no se encuentra
		when(clienteClient.obtenerClienteId(99L)).thenReturn(Collections.emptyMap());

		// When & Then
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			cuponService.saveCupon(request);
		});

		assertEquals("Cliente no encontrado, no se puede asignar el cupón", exception.getMessage());
		verify(cuponRepository, never()).save(any(Cupon.class));
	}

	@Test
	void getCuponPorIdExitosoTest() {
		// Given
		Cupon cupon = new Cupon(1L, 10L, "VERANO30", 30, LocalDate.now().plusDays(5));
		when(cuponRepository.findById(1L)).thenReturn(Optional.of(cupon));

		// When
		Cupon resultado = cuponService.getCupon(1L);

		// Then
		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals("VERANO30", resultado.getCodigo());
	}

	@Test
	void getCuponPorIdNoEncontradoTest() {
		// Given
		when(cuponRepository.findById(1L)).thenReturn(Optional.empty());

		// When & Then
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			cuponService.getCupon(1L);
		});

		assertEquals("Cupon no encontrado", exception.getMessage());
	}

	@Test
	void updateCuponTest() {
		// Given
		Cupon cuponExistente = new Cupon(1L, 10L, "ERROR10", 10, LocalDate.now());
		when(cuponRepository.findById(1L)).thenReturn(Optional.of(cuponExistente));

		CuponRequest request = new CuponRequest(10L, "ARREGLO15", 15, LocalDate.now().plusDays(10));

		Cupon cuponActualizado = new Cupon(1L, 10L, "ARREGLO15", 15, LocalDate.now().plusDays(10));
		when(cuponRepository.save(any(Cupon.class))).thenReturn(cuponActualizado);

		// When
		Cupon resultado = cuponService.updateCupon(1L, request);

		// Then
		assertNotNull(resultado);
		assertEquals(15, resultado.getPorcentajeDescuento());
		assertEquals("ARREGLO15", resultado.getCodigo());
		verify(cuponRepository, times(1)).save(cuponExistente);
	}
}