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
import com.proyecto.proyecto.model.Ubicacion;
import com.proyecto.proyecto.service.IUbicacionService;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionController {

    @Autowired IUbicacionService service;

    @PostMapping
    public MensajeResponse crear(@RequestBody Ubicacion ubicacion) {
        return service.guardar(ubicacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable String id,
            @RequestBody Ubicacion ubicacionActualizada) {

        MensajeResponse actualizado = service.actualizar(id, ubicacionActualizada);

        return ResponseEntity.ok(actualizado);
    }

    @GetMapping
    public List<Ubicacion> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Ubicacion obtenerPorId(@PathVariable ObjectId id) {
        return service.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public MensajeResponse eliminar(@PathVariable ObjectId id) {
        return service.eliminar(id);
    }
}
