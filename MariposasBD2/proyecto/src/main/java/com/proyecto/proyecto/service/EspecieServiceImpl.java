package main.java.com.proyecto.proyecto.service;

import main.java.com.proyecto.proyecto.iservice.IEspecieService;
import main.java.com.proyecto.proyecto.model.Especie;
import main.java.com.proyecto.proyecto.repository.EspecieRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EspecieServiceImpl implements IEspecieService {

    private final EspecieRepository especieRepository;

    public EspecieServiceImpl(EspecieRepository especieRepository) {
        this.especieRepository = especieRepository;
    }

    @Override
    public List<Especie> findAll() {
        return especieRepository.findAll();
    }

    @Override
    public Optional<Especie> findById(String id) {
        return especieRepository.findById(id);
    }

    @Override
    public Especie save(Especie especie) {
        return especieRepository.save(especie);
    }

    @Override
    public void delete(String id) {
        especieRepository.deleteById(id);
    }
}
