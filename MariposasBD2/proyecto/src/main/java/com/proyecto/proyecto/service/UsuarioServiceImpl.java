package com.proyecto.proyecto.service;

import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.proyecto.proyecto.model.Usuario;
import com.proyecto.proyecto.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository repository;

    @Override
    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public List<Usuario> listar() {
        return repository.findAll();
    }

    @Override
    public Usuario obtenerPorId(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(String id) {
        repository.deleteById(id);
    }
}
