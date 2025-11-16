package com.proyecto.proyecto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.proyecto.model.Observacion;

public interface IObservacionRepository extends MongoRepository<Observacion, String> { }
