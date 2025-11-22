package com.proyecto.proyecto.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.proyecto.proyecto.DTO.MensajeResponse;
import com.proyecto.proyecto.model.Ubicacion;

public interface IUbicacionService {
    MensajeResponse guardar(Ubicacion ubicacion);
    MensajeResponse actualizar(String id, Ubicacion nuevaUbicacion);
    List<Ubicacion> listar();
    Ubicacion obtenerPorId(ObjectId id);
    MensajeResponse eliminar(ObjectId id);
}
