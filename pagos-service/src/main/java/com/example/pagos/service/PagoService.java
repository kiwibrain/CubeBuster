package com.example.pagos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.pagos.dto.PagoRequest;
import com.example.pagos.model.Pago;
import com.example.pagos.repository.PagoRepository;
import com.example.pagos.webclient.ClienteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ClienteClient clienteClient;

    public List<Pago> getPagos(){
        return pagoRepository.findAll();
    }

    // Método para registrar un nuevo pago
    public Pago savePago(PagoRequest request){
        // 1. Validar si el Cliente existe en el otro microservicio
        Map<String,Object> cliente = clienteClient.obtenerClienteId(request.getClienteId());
        if(cliente == null || cliente.isEmpty()){
            throw new RuntimeException("Cliente no encontrado, no se puede procesar el pago");
        }

        // 2. Armar y guardar el pago
        Pago nuevoPago = new Pago();
        nuevoPago.setClienteId(request.getClienteId());
        nuevoPago.setMonto(request.getMonto());
        nuevoPago.setMetodoPago(request.getMetodoPago());
        nuevoPago.setFechaPago(LocalDate.now()); // Registra la fecha de la transacción automáticamente

        return pagoRepository.save(nuevoPago);
    }

    public Pago getPago(Long id){
        return pagoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }

    public Pago updatePago(Long id, PagoRequest request) {
        Pago pagoExistente = getPago(id);
        pagoExistente.setMonto(request.getMonto());
        pagoExistente.setMetodoPago(request.getMetodoPago());

        return pagoRepository.save(pagoExistente);
    }

    public void deletePago(Long id) {
        Pago pago = getPago(id);
        pagoRepository.delete(pago);
    }
}