package com.proyecto.proyecto.service;

import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.proyecto.proyecto.model.Ubicacion;
import com.proyecto.proyecto.repository.IUbicacionRepository;

@Service
public class UbicacionServiceImpl implements IUbicacionService {

    @Autowired
    private IUbicacionRepository repository;

    @Override
    public Ubicacion guardar(Ubicacion ubicacion) {
        return repository.save(ubicacion);
    }

    @Override
    public List<Ubicacion> listar() {
        return repository.findAll();
    }

    @Override
    public Ubicacion obtenerPorId(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(String id) {
        repository.deleteById(id);
    }
}
