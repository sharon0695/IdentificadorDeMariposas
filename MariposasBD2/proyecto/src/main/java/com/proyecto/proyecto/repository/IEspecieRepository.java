package com.proyecto.proyecto.repository;

import com.proyecto.proyecto.model.Especie;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IEspecieRepository extends MongoRepository<Especie, ObjectId> { }
