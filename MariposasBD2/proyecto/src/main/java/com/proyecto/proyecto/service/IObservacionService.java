package com.proyecto.proyecto.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.proyecto.proyecto.model.Observacion;

public interface IObservacionService {
    Observacion guardar(Observacion observacion);
    List<Observacion> listar();
    Observacion obtenerPorId(ObjectId id);
    void eliminar(ObjectId id);
}
