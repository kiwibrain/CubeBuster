package com.example.resenas.service;

import java.util.List;
import java.util.Map;

import com.example.resenas.dto.ReclamoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.resenas.model.Reclamo;
import com.example.resenas.repository.ReclamoRepository;
import com.example.resenas.webclient.ClienteClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReclamoService {
    @Autowired
    private ReclamoRepository reclamoRepository;
    @Autowired
    private ClienteClient clienteClient;

    public List<Reclamo> getReclamos(){
        return reclamoRepository.findAll();
    }

    //metodo para agregar un nuevo reclamo
    public Reclamo saveReclamo(Reclamo reclamo){
        Map<String,Object> cliente = clienteClient.obtenerClienteId(reclamo.getClienteId());
        if(cliente == null || cliente.isEmpty()){
            throw new RuntimeException("Cliente no encontrado, no se puede agregar reclamo");
        }
        return reclamoRepository.save(reclamo);
    }

    public Reclamo getReclamos(Long id){
        return reclamoRepository.findById(id).orElseThrow(() -> new RuntimeException("Reclamo no encontrado"));
    }

    public Reclamo updateReclamo(Long id, ReclamoRequest request) {
        Reclamo reclamoExistente = getReclamos(id);

        reclamoExistente.setMotivoReclamo(request.getMotivoReclamo());
        reclamoExistente.setDescripcionReclamo(request.getDescripcionReclamo());


        return reclamoRepository.save(reclamoExistente);
    }

    // Metodo DELETE
    public void deleteReclamo(Long id) {
        Reclamo reclamoExistente = getReclamos(id);
        reclamoRepository.delete(reclamoExistente);
    }

}
