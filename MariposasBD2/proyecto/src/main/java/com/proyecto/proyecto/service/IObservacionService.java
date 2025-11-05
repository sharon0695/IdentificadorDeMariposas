package main.java.com.proyecto.proyecto.service;

import java.util.List;
import main.java.com.proyecto.proyecto.model.Observacion;

public interface IObservacionService {
    Observacion guardar(Observacion observacion);
    List<Observacion> listar();
    Observacion obtenerPorId(String id);
    void eliminar(String id);
}
