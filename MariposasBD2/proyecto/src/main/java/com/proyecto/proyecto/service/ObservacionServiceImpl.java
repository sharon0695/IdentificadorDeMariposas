package com.proyecto.proyecto.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.proyecto.proyecto.model.Observacion;
import com.proyecto.proyecto.repository.IObservacionRepository;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ObservacionServiceImpl implements IObservacionService {

    @Autowired IObservacionRepository repository;

    @Override
    public Observacion guardar(Observacion observacion) {
        return repository.save(observacion);
    }

    @Override
    public List<Observacion> listar() {
        return repository.findAll();
    }

    @Override
    public Observacion obtenerPorId(ObjectId id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(ObjectId id) {
        repository.deleteById(id);
    }
}
