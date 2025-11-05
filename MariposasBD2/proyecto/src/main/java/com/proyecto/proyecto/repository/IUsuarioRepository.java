package main.java.com.proyecto.proyecto.repository;

import main.java.com.proyecto.proyecto.model.Especie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUsuarioRepository extends MongoRepository<Especie, String> { }
