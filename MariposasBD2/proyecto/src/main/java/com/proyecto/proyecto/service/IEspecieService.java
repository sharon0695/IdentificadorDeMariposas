package com.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.proyecto.model.Especie;

public interface IEspecieService {
    List<Especie> findAll();
    Optional<Especie> findById(String id);
    Especie save(Especie especie);
    void delete(String id);
    void agregarImagenGeneral(String idEspecie, String urlImagen);
    void agregarImagenDetallada(String idEspecie, String parte, String url);
    void actualizarUbicacion(String id, String ubic);
}
