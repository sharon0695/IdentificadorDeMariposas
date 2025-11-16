package com.proyecto.proyecto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.proyecto.model.Ubicacion;

public interface IUbicacionRepository extends MongoRepository<Ubicacion, String> { }
