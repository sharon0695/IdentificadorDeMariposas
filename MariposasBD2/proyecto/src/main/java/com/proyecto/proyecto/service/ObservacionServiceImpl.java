package com.proyecto.proyecto.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import com.proyecto.proyecto.model.Observacion;
import com.proyecto.proyecto.repository.IObservacionRepository;

import org.springframework.beans.factory.annotation.Autowired;

import com.proyecto.proyecto.DTO.MensajeResponse;
import com.proyecto.proyecto.DTO.ObservacionDTO;

@Service
public class ObservacionServiceImpl implements IObservacionService {

    @Autowired IObservacionRepository repository;

    @Override
    public MensajeResponse guardar(Observacion observacion) {
        repository.save(observacion);
        return new MensajeResponse("Observación guardada con éxito");
    }

    @Override
    public MensajeResponse actualizar(String id, Observacion nueva) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID es obligatorio");
        }

        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("El ID no tiene un formato válido");
        }

        Observacion existente = repository.findById(objectId)
                .orElseThrow(() -> new IllegalArgumentException("No existe una observación con ese ID"));

        if (nueva.getEspecieIdAsString() == null) {
            throw new IllegalArgumentException("El campo especieId es obligatorio");
        }

        if (nueva.getUsuarioIdAsString() == null) {
            throw new IllegalArgumentException("El campo usuarioId es obligatorio");
        }

        if (nueva.getComentario() == null || nueva.getComentario().isBlank()) {
            throw new IllegalArgumentException("El comentario es obligatorio");
        }

        existente.setEspecieId(new ObjectId(nueva.getEspecieIdAsString()));
        existente.setUsuarioId(new ObjectId(nueva.getUsuarioIdAsString()));
        existente.setComentario(nueva.getComentario());
        existente.setFecha(nueva.getFecha() != null ? nueva.getFecha() : existente.getFecha());

        repository.save(existente);
        return new MensajeResponse("Observación actualizada con éxito");
    }

    @Override
    public List<ObservacionDTO> listar() {
        return repository.findAll().stream().map(o ->
            new ObservacionDTO(
                o.getId() != null ? o.getIdAsString() : null,
                o.getEspecieId() != null ? o.getEspecieIdAsString() : null,
                o.getUsuarioId() != null ? o.getUsuarioIdAsString() : null,
                o.getComentario(),
                o.getFecha()
            )
        ).collect(Collectors.toList());
    }


    @Override
    public Observacion obtenerPorId(ObjectId id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public MensajeResponse eliminar(ObjectId id) {
        repository.deleteById(id);
        return new MensajeResponse("Observación eliminada con éxito");
    }
}
