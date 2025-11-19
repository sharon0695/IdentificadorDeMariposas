package com.proyecto.proyecto.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.proyecto.model.Observacion;

public interface IObservacionRepository extends MongoRepository<Observacion, String> { 
    List<Observacion> findByEspecieId(String especieId);
}
