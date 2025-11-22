package com.proyecto.proyecto.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.proyecto.DTO.EspecieDTO;
import com.proyecto.proyecto.DTO.MensajeResponse;
import com.proyecto.proyecto.model.Especie;
import com.proyecto.proyecto.service.IEspecieService;

@RestController
@RequestMapping("/api/especies")
public class EspecieController {

    @Autowired IEspecieService especieService;

    @GetMapping("listar")
    public List<EspecieDTO> listar() {
        return especieService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id) {
        Optional<Especie> especie = especieService.findById(id);
        return especie.isPresent() ? ResponseEntity.ok(especie.get())
                                   : ResponseEntity.notFound().build();
    }

    @PostMapping
    public MensajeResponse create(@RequestBody Especie especie) {
        return especieService.save(especie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEspecie(
        @PathVariable String id,
        @RequestBody Especie especieActualizada) {        
        Especie especie = especieService.update(id, especieActualizada);
            return ResponseEntity.ok(especie);
    }


    @PostMapping("/{id}/imagen-general")
    public ResponseEntity<?> agregarImagenGeneral(
            @PathVariable ObjectId id,
            @RequestParam String url) {

        especieService.agregarImagenGeneral(id, url);
        return ResponseEntity.ok("Imagen agregada");
    }

    @PostMapping("/{id}/imagen-detallada")
    public ResponseEntity<?> agregarImagenDetallada(
            @PathVariable ObjectId id,
            @RequestParam String parte,
            @RequestParam String url) {

        especieService.agregarImagenDetallada(id, parte, url);
        return ResponseEntity.ok("Imagen detallada agregada");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeResponse> delete(@PathVariable ObjectId id){
    especieService.delete(id);
    return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(new MensajeResponse("Especie eliminada con éxito"));
    }
  
    @GetMapping("/reporte")
    public ResponseEntity<byte[]> generarReporte(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        byte[] pdf = especieService.generarReporteEspecies(fechaInicio, fechaFin);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "reporte_mariposas.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }

    @GetMapping("/reporte/tipo/{tipo}")
    public ResponseEntity<byte[]> generarReportePorTipo(@PathVariable String tipo) {

        byte[] pdf = especieService.generarReportePorTipo(tipo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "attachment",
                "reporte_especies_" + tipo + ".pdf"
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }
    @GetMapping("/reporte/familia/{familia}")
    public ResponseEntity<byte[]> generarReportePorFamilia(@PathVariable String familia) {

        byte[] pdf = especieService.generarReportePorFamilia(familia);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
                "attachment",
                "reporte_especies_" + familia + ".pdf"
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }

    @GetMapping("/reporte/todo")
    public ResponseEntity<byte[]> generarReporteCompleto() {

        byte[] pdf = especieService.generarReporteCompleto();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "reporte_especies_completo.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }
}