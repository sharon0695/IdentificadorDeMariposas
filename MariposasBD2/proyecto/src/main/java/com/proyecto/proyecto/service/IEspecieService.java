package com.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import com.proyecto.proyecto.model.Especie;

public interface IEspecieService {
    List<Especie> findAll();
    Optional<Especie> findById(ObjectId id);
    Especie save(Especie especie);
    void delete(ObjectId id);
    void agregarImagenGeneral(ObjectId idEspecie, String urlImagen);
    void agregarImagenDetallada(ObjectId idEspecie, String parte, String url);
    void actualizarUbicacion(ObjectId id, ObjectId ubic);
}
