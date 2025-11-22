package com.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import com.proyecto.proyecto.DTO.MensajeResponse;
import com.proyecto.proyecto.model.Especie;

public interface IEspecieService {
    List<Especie> findAll();
    Optional<Especie> findById(ObjectId id);
    MensajeResponse save(Especie especie);
    MensajeResponse delete(ObjectId id);
    void agregarImagenGeneral(ObjectId idEspecie, String urlImagen);
    void agregarImagenDetallada(ObjectId idEspecie, String parte, String url);
    void actualizarUbicacion(ObjectId id, ObjectId ubic);
    Especie update(String id, Especie especieActualizada);
}
