package com.proyecto.proyecto.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.proyecto.proyecto.DTO.MensajeResponse;
import com.proyecto.proyecto.model.Ubicacion;
import com.proyecto.proyecto.repository.IUbicacionRepository;

@Service
public class UbicacionServiceImpl implements IUbicacionService {

    @Autowired IUbicacionRepository ubicacionRepository;

    @Override
    public MensajeResponse guardar(Ubicacion ubicacion) {
        ubicacionRepository.save(ubicacion);
        return new MensajeResponse("Ubicación guardada con éxito");
    }

    @Override
    public MensajeResponse actualizar(String id, Ubicacion nuevaUbicacion) {

        Ubicacion existente = ubicacionRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));

        // Actualizar datos simples
        existente.setLocalidad(nuevaUbicacion.getLocalidad());
        existente.setMunicipio(nuevaUbicacion.getMunicipio());
        existente.setDepartamento(nuevaUbicacion.getDepartamento());
        existente.setPais(nuevaUbicacion.getPais());
        existente.setEcosistema(nuevaUbicacion.getEcosistema());

        // Actualizar geolocalización (objeto anidado)
        if (nuevaUbicacion.getGeolocalizacion() != null) {
            Ubicacion.GeoLocalizacion geo = existente.getGeolocalizacion();
            Ubicacion.GeoLocalizacion nuevaGeo = nuevaUbicacion.getGeolocalizacion();

            if (geo == null) {
                geo = new Ubicacion.GeoLocalizacion();
            }

            geo.setLatitud(nuevaGeo.getLatitud());
            geo.setLongitud(nuevaGeo.getLongitud());
            existente.setGeolocalizacion(geo);
        }
        ubicacionRepository.save(existente);
        return new MensajeResponse("Ubicación actualizada con éxito");
    }

    @Override
    public List<Ubicacion> listar() {
        return ubicacionRepository.findAll();
    }

    @Override
    public Ubicacion obtenerPorId(ObjectId id) {
        return ubicacionRepository.findById(id).orElse(null);
    }

    @Override
    public MensajeResponse eliminar(ObjectId id) {
        ubicacionRepository.deleteById(id);
        return new MensajeResponse("Ubicación eliminada con éxito");
    }
}
