package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.Especie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IObservacionRepository extends MongoRepository<Especie, String> { }
