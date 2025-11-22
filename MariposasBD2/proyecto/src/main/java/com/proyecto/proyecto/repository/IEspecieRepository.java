package com.proyecto.proyecto.repository;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.proyecto.model.Especie;

public interface IEspecieRepository extends MongoRepository<Especie, ObjectId> { 
    List<Especie> findByFechaRegistroBetween(Date inicio, Date fin);
    List<Especie> findByTipoEspecieIgnoreCase(String tipoEspecie);
    List<Especie> findByFamiliaIgnoreCase(String familia);
}
