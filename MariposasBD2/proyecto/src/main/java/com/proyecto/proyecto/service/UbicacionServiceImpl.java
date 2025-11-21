package com.proyecto.proyecto.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import com.proyecto.proyecto.model.Ubicacion;
import com.proyecto.proyecto.repository.IUbicacionRepository;

@Service
public class UbicacionServiceImpl implements IUbicacionService {

    @Autowired IUbicacionRepository ubicacionRepository;

    @Override
    public Ubicacion guardar(Ubicacion ubicacion) {
        return ubicacionRepository.save(ubicacion);
    }

    @Override
    public List<Ubicacion> listar() {
        return ubicacionRepository.findAll();
    }

    @Override
    public Ubicacion obtenerPorId(ObjectId id) {
        return ubicacionRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(ObjectId id) {
        ubicacionRepository.deleteById(id);
    }
}
