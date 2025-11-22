package com.proyecto.proyecto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.DTO.EspecieDTO;
import com.proyecto.proyecto.DTO.MensajeResponse;
import com.proyecto.proyecto.model.Especie;
import com.proyecto.proyecto.repository.IEspecieRepository;

@Service
public class EspecieServiceImpl implements IEspecieService {

    @Autowired IEspecieRepository especieRepository;    

    @Override
    public List<EspecieDTO> listar() {
        return especieRepository.findAll()
                .stream()
                .map(EspecieDTO::new)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<Especie> findById(ObjectId id) {
        return especieRepository.findById(id);
    }

    @Override
    public MensajeResponse save(Especie especie) {
       especieRepository.save(especie);
       return new MensajeResponse("Especie guardada exitosamente");
    }

    @Override
    public Especie update(String id, Especie especieActualizada) {

        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (Exception e) {
            throw new RuntimeException("ID inválido: " + id);
        }

        Especie existente = especieRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Especie no encontrada"));

        existente.setNombreCientifico(especieActualizada.getNombreCientifico());
        existente.setNombreComun(especieActualizada.getNombreComun());
        existente.setFamilia(especieActualizada.getFamilia());
        existente.setTipoEspecie(especieActualizada.getTipoEspecie());
        existente.setDescripcion(especieActualizada.getDescripcion());
        existente.setFechaRegistro(especieActualizada.getFechaRegistro());

        existente.setImagenes(especieActualizada.getImagenes());
        existente.setImagenesDetalladas(especieActualizada.getImagenesDetalladas());
        existente.setCaracteristicasMorfo(especieActualizada.getCaracteristicasMorfo());

        if (especieActualizada.getUbicacionRecoleccion() != null) {
            existente.setUbicacionRecoleccion(especieActualizada.getUbicacionRecoleccion());
        }

        if (especieActualizada.getRegistradoPor() != null) {
            existente.setRegistradoPor(especieActualizada.getRegistradoPor());
        }

        return especieRepository.save(existente);
    }

    @Override
    public void agregarImagenGeneral(ObjectId idEspecie, String urlImagen) {
        Especie especie = especieRepository.findById(idEspecie)
                .orElseThrow(() -> new RuntimeException("Especie no encontrada"));

        if (especie.getImagenes() == null) {
            especie.setImagenes(new ArrayList<>());
        }

        especie.getImagenes().add(urlImagen);
        especieRepository.save(especie);
    }

    @Override
    public void agregarImagenDetallada(ObjectId idEspecie, String parte, String url) {
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
    public void actualizarUbicacion(ObjectId id, ObjectId ubic) {
        Especie esp = especieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especie no encontrada"));

        esp.setUbicacionRecoleccion(ubic);
        especieRepository.save(esp);
    }

    @Override
    public MensajeResponse delete(ObjectId id) {
        especieRepository.deleteById(id);
        return new MensajeResponse("Especie eliminada con éxito");
    }
}
