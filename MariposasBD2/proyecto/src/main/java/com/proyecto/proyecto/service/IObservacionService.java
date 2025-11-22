package com.proyecto.proyecto.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.proyecto.proyecto.DTO.MensajeResponse;
import com.proyecto.proyecto.DTO.ObservacionDTO;
import com.proyecto.proyecto.model.Observacion;

public interface IObservacionService {
    MensajeResponse guardar(Observacion observacion);
    MensajeResponse actualizar(String id, Observacion nueva);
    List<ObservacionDTO> listar();
    Observacion obtenerPorId(ObjectId id);
    MensajeResponse eliminar(ObjectId id);
}
