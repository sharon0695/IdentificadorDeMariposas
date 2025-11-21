package com.proyecto.proyecto.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.proyecto.model.Ubicacion;

public interface IUbicacionRepository extends MongoRepository<Ubicacion, ObjectId> { }
