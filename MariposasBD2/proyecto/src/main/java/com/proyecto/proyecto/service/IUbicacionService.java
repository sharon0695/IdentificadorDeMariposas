package com.proyecto.proyecto.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.proyecto.proyecto.model.Ubicacion;

public interface IUbicacionService {
    Ubicacion guardar(Ubicacion ubicacion);
    List<Ubicacion> listar();
    Ubicacion obtenerPorId(ObjectId id);
    void eliminar(ObjectId id);
}
