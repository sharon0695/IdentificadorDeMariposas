package main.java.com.proyecto.proyecto.controller;

import main.java.com.proyecto.proyecto.model.Observacion;
import main.java.com.proyecto.proyecto.service.IObservacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/observaciones")
@CrossOrigin(origins = "*")
public class ObservacionController {

    @Autowired
    private IObservacionService service;

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
