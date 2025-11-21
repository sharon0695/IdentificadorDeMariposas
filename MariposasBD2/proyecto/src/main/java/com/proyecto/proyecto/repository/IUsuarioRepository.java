package com.proyecto.proyecto.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.proyecto.model.Usuario;

public interface IUsuarioRepository extends MongoRepository<Usuario, ObjectId> {
    Optional<Usuario> findByCorreo(String correo);
}
