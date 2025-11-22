package com.proyecto.proyecto.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.proyecto.proyecto.DTO.LoginRequest;
import com.proyecto.proyecto.DTO.LoginResponse;
import com.proyecto.proyecto.DTO.MensajeResponse;
import com.proyecto.proyecto.DTO.UsuarioUpdateRequest;
import com.proyecto.proyecto.model.Usuario;

public interface IUsuarioService {
    Usuario guardar(Usuario usuario);
    MensajeResponse actualizar(String id, UsuarioUpdateRequest request);
    LoginResponse login(LoginRequest request);
    List<Usuario> listar();
    Usuario obtenerPorId(ObjectId id);
    MensajeResponse eliminar(ObjectId id);
    void logout(String authHeader);
}
