package com.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.model.Especie;
import com.proyecto.proyecto.repository.IEspecieRepository;

@Service
public class EspecieServiceImpl implements IEspecieService {

    @Autowired IEspecieRepository especieRepository;    

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
