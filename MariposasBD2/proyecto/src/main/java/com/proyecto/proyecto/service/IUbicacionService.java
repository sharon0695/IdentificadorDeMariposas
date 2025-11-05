package main.java.com.proyecto.proyecto.service;

import java.util.List;
import main.java.com.proyecto.proyecto.model.Ubicacion;

public interface IUbicacionService {
    Ubicacion guardar(Ubicacion ubicacion);
    List<Ubicacion> listar();
    Ubicacion obtenerPorId(String id);
    void eliminar(String id);
}
