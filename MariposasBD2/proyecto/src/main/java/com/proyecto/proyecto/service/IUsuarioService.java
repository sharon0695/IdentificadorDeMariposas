package com.proyecto.proyecto.service;

import java.util.List;

import com.proyecto.proyecto.DTO.LoginRequest;
import com.proyecto.proyecto.DTO.LoginResponse;
import com.proyecto.proyecto.model.Usuario;

public interface IUsuarioService {
    Usuario guardar(Usuario usuario);
    LoginResponse login(LoginRequest request);
    List<Usuario> listar();
    Usuario obtenerPorId(String id);
    void eliminar(String id);
    void logout(String authHeader);
}
