package com.example;

import duoc.clientes.model.Cliente;
import duoc.clientes.repository.ClienteRepository;
import duoc.clientes.service.ClienteService;
import duoc.clientes.exception.ClienteNoEncontradoException;
import duoc.clientes.dto.ClienteRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository; // Fingimos la base de datos

	@InjectMocks
	private ClienteService clienteService; // El servicio real que vamos a exprimir

	@Test
	void listarTodosTest() {
		// 1. Preparar el escenario (Given)
		Cliente cliente1 = new Cliente(1L, "Juan", "Perez", "juan@mail.com");
		Cliente cliente2 = new Cliente(2L, "Maria", "Gomez", "maria@mail.com");
		when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

		// 2. Ejecutar la acción (When)
		List<Cliente> resultado = clienteService.listarTodos();

		// 3. Verificar el resultado (Then)
		assertEquals(2, resultado.size());
		assertEquals("Juan", resultado.get(0).getNombre());
		verify(clienteRepository, times(1)).findAll();
	}

	@Test
	void buscarPorIdExitoTest() {
		// Given
		Cliente cliente = new Cliente(1L, "Juan", "Perez", "juan@mail.com");
		when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

		// When
		Cliente resultado = clienteService.buscarPorId(1L);

		// Then
		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals("juan@mail.com", resultado.getEmail());
	}

	@Test
	void buscarPorIdErrorTest() {
		// Given: Le decimos que cuando busque el ID 99, devuelva vacío
		when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

		// When & Then: Esperamos que lance la excepción exacta
		assertThrows(ClienteNoEncontradoException.class, () -> {
			clienteService.buscarPorId(99L);
		});
	}

	@Test
	void crearDesdeRequestTest() {
		// Given
		// Asumo que tienes setters o constructor en tu DTO ClienteRequest
		ClienteRequest request = new ClienteRequest();
		request.setNombre("Pedro");
		request.setApellido("Diaz");
		request.setEmail("pedro@mail.com");

		Cliente clienteGuardado = new Cliente(1L, "Pedro", "Diaz", "pedro@mail.com");

		// Cuando guarde cualquier objeto de tipo Cliente, retorna mi clienteGuardado
		when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteGuardado);

		// When
		Cliente resultado = clienteService.crearDesdeRequest(request);

		// Then
		assertNotNull(resultado);
		assertEquals("Pedro", resultado.getNombre());
		verify(clienteRepository, times(1)).save(any(Cliente.class));
	}

	@Test
	void actualizarTest() {
		// Given
		Cliente clienteExistente = new Cliente(1L, "Juan", "Perez", "juan@mail.com");
		when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));

		ClienteRequest request = new ClienteRequest();
		request.setNombre("Juan Modificado");
		request.setApellido("Perez");
		request.setEmail("juan_nuevo@mail.com");

		Cliente clienteActualizado = new Cliente(1L, "Juan Modificado", "Perez", "juan_nuevo@mail.com");
		when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteActualizado);

		// When
		Cliente resultado = clienteService.actualizar(1L, request);

		// Then
		assertEquals("Juan Modificado", resultado.getNombre());
		assertEquals("juan_nuevo@mail.com", resultado.getEmail());
	}

	@Test
	void eliminarTest() {
		// Given
		Cliente clienteExistente = new Cliente(1L, "Juan", "Perez", "juan@mail.com");
		when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));

		// Para delete, no retornamos nada (void), solo queremos verificar que se llame
		doNothing().when(clienteRepository).delete(clienteExistente);

		// When
		clienteService.eliminar(1L);

		// Then
		verify(clienteRepository, times(1)).findById(1L);
		verify(clienteRepository, times(1)).delete(clienteExistente);
	}

	@Test
	void simularErrorTest() {
		// When & Then
		assertThrows(RuntimeException.class, () -> {
			clienteService.simularError();
		});
	}
}