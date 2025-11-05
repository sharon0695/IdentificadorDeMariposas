package main.java.com.proyecto.proyecto.service;

import org.springframework.stereotype.Service;
import java.util.List;
import main.java.com.proyecto.proyecto.model.Observacion;
import main.java.com.proyecto.proyecto.repository.ObservacionRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ObservacionServiceImpl implements IObservacionService {

    @Autowired
    private ObservacionRepository repository;

    @Override
    public Observacion guardar(Observacion observacion) {
        return repository.save(observacion);
    }

    @Override
    public List<Observacion> listar() {
        return repository.findAll();
    }

    @Override
    public Observacion obtenerPorId(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(String id) {
        repository.deleteById(id);
    }
}
