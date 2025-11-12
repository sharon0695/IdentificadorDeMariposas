package com.proyecto.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.proyecto.proyecto.model.Ubicacion;
import com.proyecto.proyecto.service.IUbicacionService;

@RestController
@RequestMapping("/api/ubicaciones")
@CrossOrigin(origins = "*")
public class UbicacionController {

    @Autowired
    private IUbicacionService service;

    @PostMapping
    public Ubicacion crear(@RequestBody Ubicacion ubicacion) {
        return service.guardar(ubicacion);
    }

    @GetMapping
    public List<Ubicacion> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Ubicacion obtenerPorId(@PathVariable String id) {
        return service.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {
        service.eliminar(id);
    }
}
