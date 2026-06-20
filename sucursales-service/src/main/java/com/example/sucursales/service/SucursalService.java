package com.example.sucursales.service;

import java.util.List;

import com.example.sucursales.dto.SucursalRequest;
import com.example.sucursales.model.Sucursal;
import com.example.sucursales.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> getSucursales(){
        return sucursalRepository.findAll();
    }

    public Sucursal saveSucursal(SucursalRequest request){
        Sucursal nuevaSucursal = new Sucursal();
        nuevaSucursal.setNombreSucursal(request.getNombreSucursal());
        nuevaSucursal.setDireccionSucursal(request.getDireccionSucursal());
        nuevaSucursal.setTelefonoSucursal(request.getTelefonoSucursal());
        nuevaSucursal.setHorarioAtencion(request.getHorarioAtencion());

        return sucursalRepository.save(nuevaSucursal);
    }

    public Sucursal getSucursal(Long id){
        return sucursalRepository.findById(id).orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
    }

    public Sucursal updateSucursal(Long id, SucursalRequest request) {
        Sucursal sucursalExistente = getSucursal(id);
        sucursalExistente.setNombreSucursal(request.getNombreSucursal());
        sucursalExistente.setDireccionSucursal(request.getDireccionSucursal());
        sucursalExistente.setTelefonoSucursal(request.getTelefonoSucursal());
        sucursalExistente.setHorarioAtencion(request.getHorarioAtencion());

        return sucursalRepository.save(sucursalExistente);
    }

    public void deleteSucursal(Long id) {
        Sucursal sucursal = getSucursal(id);
        sucursalRepository.delete(sucursal);
    }
}