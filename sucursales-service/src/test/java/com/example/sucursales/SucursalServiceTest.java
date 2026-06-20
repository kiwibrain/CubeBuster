package com.example.sucursales;

import com.example.sucursales.dto.SucursalRequest;
import com.example.sucursales.model.Sucursal;
import com.example.sucursales.repository.SucursalRepository;
import com.example.sucursales.service.SucursalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SucursalServiceTest {

	@Mock
	private SucursalRepository sucursalRepository;

	@InjectMocks
	private SucursalService sucursalService;

	@Test
	void listarTodosTest() {
		// Given
		Sucursal sucursal1 = new Sucursal(1L, "CubeBuster Centro", "Av. Principal 123", "555-1234", "09:00 - 20:00");
		Sucursal sucursal2 = new Sucursal(2L, "CubeBuster Norte", "Calle Norte 456", "555-5678", "10:00 - 21:00");
		when(sucursalRepository.findAll()).thenReturn(Arrays.asList(sucursal1, sucursal2));

		// When
		List<Sucursal> resultado = sucursalService.getSucursales();

		// Then
		assertNotNull(resultado);
		assertEquals(2, resultado.size());
		assertEquals("CubeBuster Centro", resultado.get(0).getNombreSucursal());
		assertEquals("Calle Norte 456", resultado.get(1).getDireccionSucursal());
		verify(sucursalRepository, times(1)).findAll();
	}

	@Test
	void saveSucursalExitosoTest() {
		// Given
		SucursalRequest request = new SucursalRequest("CubeBuster Sur", "Av. Sur 789", "555-9012", "10:00 - 22:00");

		Sucursal sucursalGuardada = new Sucursal(1L, "CubeBuster Sur", "Av. Sur 789", "555-9012", "10:00 - 22:00");
		when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursalGuardada);

		// When
		Sucursal resultado = sucursalService.saveSucursal(request);

		// Then
		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals("CubeBuster Sur", resultado.getNombreSucursal());
		verify(sucursalRepository, times(1)).save(any(Sucursal.class));
	}

	@Test
	void getSucursalPorIdExitosoTest() {
		// Given
		Sucursal sucursal = new Sucursal(1L, "CubeBuster Plaza", "Plaza Central", "555-0000", "09:00 - 18:00");
		when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursal));

		// When
		Sucursal resultado = sucursalService.getSucursal(1L);

		// Then
		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals("CubeBuster Plaza", resultado.getNombreSucursal());
	}

	@Test
	void getSucursalPorIdNoEncontradoTest() {
		// Given
		when(sucursalRepository.findById(1L)).thenReturn(Optional.empty());

		// When & Then
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			sucursalService.getSucursal(1L);
		});

		assertEquals("Sucursal no encontrada", exception.getMessage());
	}

	@Test
	void updateSucursalTest() {
		// Given
		Sucursal sucursalExistente = new Sucursal(1L, "CubeBuster Viejo", "Calle 1", "111", "Cerrado");
		when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursalExistente));

		SucursalRequest request = new SucursalRequest("CubeBuster Renovado", "Calle 1", "222", "Abierto 24/7");

		Sucursal sucursalActualizada = new Sucursal(1L, "CubeBuster Renovado", "Calle 1", "222", "Abierto 24/7");
		when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursalActualizada);

		// When
		Sucursal resultado = sucursalService.updateSucursal(1L, request);

		// Then
		assertNotNull(resultado);
		assertEquals("CubeBuster Renovado", resultado.getNombreSucursal());
		assertEquals("Abierto 24/7", resultado.getHorarioAtencion());
		verify(sucursalRepository, times(1)).save(sucursalExistente);
	}

	@Test
	void deleteSucursalTest() {
		// Given
		Sucursal sucursalExistente = new Sucursal(1L, "CubeBuster Centro", "Av. Principal 123", "555-1234", "09:00 - 20:00");
		when(sucursalRepository.findById(1L)).thenReturn(Optional.of(sucursalExistente));

		// When
		sucursalService.deleteSucursal(1L);

		// Then
		verify(sucursalRepository, times(1)).delete(sucursalExistente);
	}
}