package com.proyecto.proyecto.service;

import java.util.ArrayList;
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
    public void agregarImagenGeneral(String idEspecie, String urlImagen) {
        Especie especie = especieRepository.findById(idEspecie)
                .orElseThrow(() -> new RuntimeException("Especie no encontrada"));

        if (especie.getImagenes() == null) {
            especie.setImagenes(new ArrayList<>());
        }

        especie.getImagenes().add(urlImagen);
        especieRepository.save(especie);
    }

    @Override
    public void agregarImagenDetallada(String idEspecie, String parte, String url) {
        Especie especie = especieRepository.findById(idEspecie)
                .orElseThrow(() -> new RuntimeException("Especie no encontrada"));

        if (especie.getImagenesDetalladas() == null) {
            especie.setImagenesDetalladas(new Especie.ImagenesDetalladas());
        }

        switch (parte.toLowerCase()) {
            case "ala_izquierda" -> especie.getImagenesDetalladas().setAlaIzquierda(url);
            case "ala_derecha" -> especie.getImagenesDetalladas().setAlaDerecha(url);
            case "antenas" -> especie.getImagenesDetalladas().setAntenas(url);
            case "cuerpo" -> especie.getImagenesDetalladas().setCuerpo(url);
            case "patas" -> especie.getImagenesDetalladas().setPatas(url);
            case "cabeza" -> especie.getImagenesDetalladas().setCabeza(url);
            default -> throw new RuntimeException("Parte no válida");
        }

        especieRepository.save(especie);
    }

    @Override
    public void delete(String id) {
        especieRepository.deleteById(id);
    }
}
