package com.proyecto.proyecto.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.proyecto.DTO.MensajeResponse;
import com.proyecto.proyecto.DTO.ObservacionDTO;
import com.proyecto.proyecto.model.Observacion;
import com.proyecto.proyecto.repository.IObservacionRepository;
import com.proyecto.proyecto.service.IObservacionService;

@RestController
@RequestMapping("/api/observaciones")
public class ObservacionController {

    @Autowired IObservacionService service;
    @Autowired IObservacionRepository observacionRepository;

    @PostMapping("/crear")
    public MensajeResponse crear(@RequestBody Observacion observacion) {
        return service.guardar(observacion);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(
        @PathVariable String id,
        @RequestBody Observacion actualizada) {
        MensajeResponse result = service.actualizar(id, actualizada);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public List<ObservacionDTO> listar() {
        return service.listar();
    }

    @GetMapping("/especie/{id}")
    public List<Observacion> listarPorEspecie(@PathVariable String id) {
        return observacionRepository.findByEspecieId(id);
    }

    @GetMapping("/{id}")
    public Observacion obtenerPorId(@PathVariable ObjectId id) {
        return service.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public MensajeResponse eliminar(@PathVariable ObjectId id) {
        return service.eliminar(id);
    }
}
