package com.proyecto.proyecto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.proyecto.model.Observacion;
import com.proyecto.proyecto.service.IObservacionService;

@RestController
@RequestMapping("/api/observaciones")
public class ObservacionController {

    @Autowired IObservacionService service;

    @PostMapping
    public Observacion crear(@RequestBody Observacion observacion) {
        return service.guardar(observacion);
    }

    @GetMapping
    public List<Observacion> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Observacion obtenerPorId(@PathVariable String id) {
        return service.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {
        service.eliminar(id);
    }
}
