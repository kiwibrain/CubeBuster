package com.example.cupones.service;

import java.util.List;
import java.util.Map;

import com.example.cupones.dto.ResenaRequest;
import com.example.cupones.webclient.JuegoClient;
import com.example.cupones.webclient.PeliculaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cupones.model.Resena;
import com.example.cupones.repository.ResenaRepository;
import com.example.cupones.webclient.ClienteClient;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResenaService {
    @Autowired
    private ResenaRepository resenaRepository;
    @Autowired
    private ClienteClient clienteClient;
    @Autowired
    private PeliculaClient peliculaClient;
    @Autowired
    private JuegoClient juegoClient;

    public List<Resena> getResenas(){
        return resenaRepository.findAll();
    }

    //metodo para agregar un nuevo reclamo
    public Resena saveResena(ResenaRequest request){
        // 1. Validar Cliente
        Map<String,Object> cliente = clienteClient.obtenerClienteId(request.getClienteId());
        if(cliente == null || cliente.isEmpty()){
            throw new RuntimeException("Cliente no encontrado, no se puede agregar la reseña");
        }

        // 2. Lógica del Nickname: Si viene vacío, usamos el nombre del cliente
        String nickname = request.getNicknameCliente();
        if (nickname == null || nickname.trim().isEmpty()) {
            nickname = (String) cliente.get("nombre"); // Asegúrate de que "nombre" es la llave correcta de tu cliente
        }

        // 3. Buscar nombre del Juego o Película
        String nombreExtraido = "";
        if (request.getIdJuego() != null) {
            Map<String,Object> juego = juegoClient.obtenerJuegoId(request.getIdJuego());
            nombreExtraido = (String) juego.get("titulo"); // O la llave que use tu microservicio juegos
        } else if (request.getIdPelicula() != null) {
            Map<String,Object> pelicula = peliculaClient.obtenerPeliculaId(request.getIdPelicula());
            nombreExtraido = (String) pelicula.get("titulo"); // O la llave que use tu microservicio peliculas
        } else {
            throw new RuntimeException("Debe ingresar el ID de un juego o de una película para reseñar");
        }

        // 4. Armar y guardar la reseña
        Resena nuevaResena = new Resena();
        nuevaResena.setClienteId(request.getClienteId());
        nuevaResena.setIdJuego(request.getIdJuego());
        nuevaResena.setIdPelicula(request.getIdPelicula());
        nuevaResena.setNombreItem(nombreExtraido);
        nuevaResena.setDescripcionResena(request.getDescripcionResena());
        nuevaResena.setNicknameCliente(nickname);

        return resenaRepository.save(nuevaResena);
    }

    public Resena getResena(Long id){
        return resenaRepository.findById(id).orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
    }

    public Resena updateResena(Long id, ResenaRequest request) {
        Resena resenaExistente = getResena(id);
        resenaExistente.setDescripcionResena(request.getDescripcionResena());

        if (request.getNicknameCliente() != null && !request.getNicknameCliente().trim().isEmpty()) {
            resenaExistente.setNicknameCliente(request.getNicknameCliente());
        }

        return resenaRepository.save(resenaExistente);
    }

    // Metodo DELETE
    public void deleteResena(Long id) {
        Resena resenaExistente = getResena(id);
        resenaRepository.delete(resenaExistente);
    }

}
