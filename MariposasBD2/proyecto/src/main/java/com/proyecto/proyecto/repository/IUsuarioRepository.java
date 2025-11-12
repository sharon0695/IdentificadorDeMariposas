package com.proyecto.proyecto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.proyecto.proyecto.model.Usuario;

public interface IUsuarioRepository extends MongoRepository<Usuario, String> {
}
