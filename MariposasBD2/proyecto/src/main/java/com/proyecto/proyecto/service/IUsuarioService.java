package com.proyecto.proyecto.service;

import java.util.List;
import com.proyecto.proyecto.model.Usuario;

public interface IUsuarioService {
    Usuario guardar(Usuario usuario);
    List<Usuario> listar();
    Usuario obtenerPorId(String id);
    void eliminar(String id);
}
