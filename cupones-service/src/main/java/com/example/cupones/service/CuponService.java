package com.example.cupones.service;

import java.util.List;
import java.util.Map;

import com.example.cupones.dto.CuponRequest;
import com.example.cupones.model.Cupon;
import com.example.cupones.repository.CuponRepository;
import com.example.cupones.webclient.ClienteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    @Autowired
    private ClienteClient clienteClient;

    public List<Cupon> getCupones(){
        return cuponRepository.findAll();
    }

    public Cupon saveCupon(CuponRequest request){
        // Validar si el Cliente existe en el otro microservicio
        Map<String,Object> cliente = clienteClient.obtenerClienteId(request.getClienteId());
        if(cliente == null || cliente.isEmpty()){
            throw new RuntimeException("Cliente no encontrado, no se puede asignar el cupón");
        }

        Cupon nuevoCupon = new Cupon();
        nuevoCupon.setClienteId(request.getClienteId());
        nuevoCupon.setCodigo(request.getCodigo());
        nuevoCupon.setPorcentajeDescuento(request.getPorcentajeDescuento());
        nuevoCupon.setFechaExpiracion(request.getFechaExpiracion());

        return cuponRepository.save(nuevoCupon);
    }

    public Cupon getCupon(Long id){
        return cuponRepository.findById(id).orElseThrow(() -> new RuntimeException("Cupon no encontrado"));
    }

    public Cupon updateCupon(Long id, CuponRequest request) {
        Cupon cuponExistente = getCupon(id);
        cuponExistente.setCodigo(request.getCodigo());
        cuponExistente.setPorcentajeDescuento(request.getPorcentajeDescuento());
        cuponExistente.setFechaExpiracion(request.getFechaExpiracion());

        return cuponRepository.save(cuponExistente);
    }

    public void deleteCupon(Long id) {
        Cupon cupon = getCupon(id);
        cuponRepository.delete(cupon);
    }
}