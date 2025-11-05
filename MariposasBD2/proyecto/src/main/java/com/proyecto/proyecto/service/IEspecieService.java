package main.java.com.proyecto.proyecto.service;

import main.java.com.proyecto.proyecto.model.Especie;
import java.util.List;
import java.util.Optional;

public interface IEspecieService {
    List<Especie> findAll();
    Optional<Especie> findById(String id);
    Especie save(Especie especie);
    void delete(String id);
}
