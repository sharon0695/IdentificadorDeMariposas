package main.java.com.proyecto.proyecto.controller;

import main.java.com.proyecto.proyecto.model.Especie;
import main.java.com.proyecto.proyecto.iservice.IEspecieService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/especies")
public class EspecieController {

    private final IEspecieService especieService;

    public EspecieController(IEspecieService especieService) {
        this.especieService = especieService;
    }

    @GetMapping
    public List<Especie> getAll() {
        return especieService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Especie> getById(@PathVariable String id) {
        return especieService.findById(id);
    }

    @PostMapping
    public Especie create(@RequestBody Especie especie) {
        return especieService.save(especie);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        especieService.delete(id);
    }
}
