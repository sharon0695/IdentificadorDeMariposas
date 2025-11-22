package com.proyecto.proyecto.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.proyecto.model.Observacion;

public interface IObservacionRepository extends MongoRepository<Observacion, ObjectId> { 
    List<Observacion> findByEspecieId(ObjectId especieId);
}

