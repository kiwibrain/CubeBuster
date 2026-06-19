package com.example.pagos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.pagos.dto.MultaRequest;
import com.example.pagos.model.Multa;
import com.example.pagos.repository.MultaRepository;
import com.example.pagos.webclient.ClienteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class MultaService {
    @Autowired
    private MultaRepository multaRepository;
    @Autowired
    private ClienteClient clienteClient;

    public List<Multa> getMultas(){
        return multaRepository.findAll();
    }

    // Método para registrar una nueva multa
    public Multa saveMultas(MultaRequest request){
        // 1. Validar si el Cliente existe en el otro microservicio
        Map<String,Object> cliente = clienteClient.obtenerClienteId(request.getClienteId());
        if(cliente == null || cliente.isEmpty()){
            throw new RuntimeException("Cliente no encontrado, no se puede agregar la multa");
        }

        // 2. Armar y guardar la multa
        Multa nuevaMulta = new Multa();
        nuevaMulta.setClienteId(request.getClienteId());
        nuevaMulta.setMotivo(request.getMotivo());
        nuevaMulta.setMonto(request.getMonto());
        nuevaMulta.setEstado("PENDIENTE"); // Se inicializa automáticamente como pendiente de pago
        nuevaMulta.setFechaEmision(LocalDate.now()); // Guarda de forma automática la fecha del día de hoy

        return multaRepository.save(nuevaMulta);
    }

    public Multa getMulta(Long id){
        return multaRepository.findById(id).orElseThrow(() -> new RuntimeException("Multa no encontrada"));
    }

    public Multa updateMulta(Long id, MultaRequest request) {
        Multa multaExistente = getMulta(id);
        multaExistente.setMotivo(request.getMotivo());
        multaExistente.setMonto(request.getMonto()); // También actualizamos el monto por si hubo un cambio

        return multaRepository.save(multaExistente);
    }

    // Método DELETE (por si tu controlador lo requiere completar)
    public void eliminarMulta(Long id) {
        Multa multa = getMulta(id);
        multaRepository.delete(multa);
    }
}