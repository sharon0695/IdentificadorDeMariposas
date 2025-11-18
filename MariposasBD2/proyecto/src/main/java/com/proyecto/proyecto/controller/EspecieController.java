package com.proyecto.proyecto.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.proyecto.model.Especie;
import com.proyecto.proyecto.service.IEspecieService;

@RestController
@RequestMapping("/api/especies")
public class EspecieController {

    @Autowired IEspecieService especieService;

    @GetMapping("/listar")
    public List<Especie> getAll() {
        return especieService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Optional<Especie> especie = especieService.findById(id);
        return especie.isPresent() ? ResponseEntity.ok(especie.get())
                                   : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Especie create(@RequestBody Especie especie) {
        return especieService.save(especie);
    }

    @PostMapping("/{id}/imagen-general")
    public ResponseEntity<?> agregarImagenGeneral(
            @PathVariable String id,
            @RequestParam String url) {

        especieService.agregarImagenGeneral(id, url);
        return ResponseEntity.ok("Imagen agregada");
    }

    @PostMapping("/{id}/imagen-detallada")
    public ResponseEntity<?> agregarImagenDetallada(
            @PathVariable String id,
            @RequestParam String parte,
            @RequestParam String url) {

        especieService.agregarImagenDetallada(id, parte, url);
        return ResponseEntity.ok("Imagen detallada agregada");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        especieService.delete(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarUbicacion(@PathVariable String id,
                                                @RequestBody Map<String, String> body) {
        String ubic = body.get("ubicacionRecoleccion");
        especieService.actualizarUbicacion(id, ubic);
        return ResponseEntity.ok("Ubicación actualizada");
    }
}
