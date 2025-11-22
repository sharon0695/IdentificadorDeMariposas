package com.proyecto.proyecto.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.proyecto.proyecto.DTO.EspecieDTO;
import com.proyecto.proyecto.DTO.MensajeResponse;
import com.proyecto.proyecto.model.Especie;
import com.proyecto.proyecto.repository.IEspecieRepository;

@Service
public class EspecieServiceImpl implements IEspecieService {

    @Autowired IEspecieRepository especieRepository;    

    @Override
    public List<EspecieDTO> listar() {
        return especieRepository.findAll()
                .stream()
                .map(EspecieDTO::new)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<Especie> findById(ObjectId id) {
        return especieRepository.findById(id);
    }

    @Override
    public MensajeResponse save(Especie especie) {
       especieRepository.save(especie);
       return new MensajeResponse("Especie guardada exitosamente");
    }

    @Override
    public Especie update(String id, Especie especieActualizada) {

        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (Exception e) {
            throw new RuntimeException("ID inválido: " + id);
        }

        Especie existente = especieRepository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Especie no encontrada"));

        existente.setNombreCientifico(especieActualizada.getNombreCientifico());
        existente.setNombreComun(especieActualizada.getNombreComun());
        existente.setFamilia(especieActualizada.getFamilia());
        existente.setTipoEspecie(especieActualizada.getTipoEspecie());
        existente.setDescripcion(especieActualizada.getDescripcion());
        existente.setFechaRegistro(especieActualizada.getFechaRegistro());

        existente.setImagenes(especieActualizada.getImagenes());
        existente.setImagenesDetalladas(especieActualizada.getImagenesDetalladas());
        existente.setCaracteristicasMorfo(especieActualizada.getCaracteristicasMorfo());

        if (especieActualizada.getUbicacionRecoleccion() != null) {
            existente.setUbicacionRecoleccion(especieActualizada.getUbicacionRecoleccion());
        }

        if (especieActualizada.getRegistradoPor() != null) {
            existente.setRegistradoPor(especieActualizada.getRegistradoPor());
        }

        return especieRepository.save(existente);
    }

    @Override
    public void agregarImagenGeneral(ObjectId idEspecie, String urlImagen) {
        Especie especie = especieRepository.findById(idEspecie)
                .orElseThrow(() -> new RuntimeException("Especie no encontrada"));

        if (especie.getImagenes() == null) {
            especie.setImagenes(new ArrayList<>());
        }

        especie.getImagenes().add(urlImagen);
        especieRepository.save(especie);
    }

    @Override
    public void agregarImagenDetallada(ObjectId idEspecie, String parte, String url) {
        Especie especie = especieRepository.findById(idEspecie)
                .orElseThrow(() -> new RuntimeException("Especie no encontrada"));

        if (especie.getImagenesDetalladas() == null) {
            especie.setImagenesDetalladas(new Especie.ImagenesDetalladas());
        }

        switch (parte.toLowerCase()) {
            case "ala_izquierda" -> especie.getImagenesDetalladas().setAlaIzquierda(url);
            case "ala_derecha" -> especie.getImagenesDetalladas().setAlaDerecha(url);
            case "antenas" -> especie.getImagenesDetalladas().setAntenas(url);
            case "cuerpo" -> especie.getImagenesDetalladas().setCuerpo(url);
            case "patas" -> especie.getImagenesDetalladas().setPatas(url);
            case "cabeza" -> especie.getImagenesDetalladas().setCabeza(url);
            default -> throw new RuntimeException("Parte no válida");
        }

        especieRepository.save(especie);
    }

    @Override
    public void actualizarUbicacion(ObjectId id, ObjectId ubic) {
        Especie esp = especieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especie no encontrada"));

        esp.setUbicacionRecoleccion(ubic);
        especieRepository.save(esp);
    }

    @Override
    public MensajeResponse delete(ObjectId id) {
        especieRepository.deleteById(id);
        return new MensajeResponse("Especie eliminada con éxito");
    }

    @Override
    public byte[] generarReporteEspecies(LocalDate fechaInicio, LocalDate fechaFin) {

        Date inicio = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fin = Date.from(fechaFin.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Obtener especies por rango
        List<Especie> especies = especieRepository
                .findByFechaRegistroBetween(inicio, fin);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font tituloFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Reporte de Mariposas Registradas", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            // Rango de fechas
            Paragraph rango = new Paragraph(
                    "Desde: " + fechaInicio + "     Hasta: " + fechaFin,
                    new Font(Font.HELVETICA, 12)
            );
            rango.setAlignment(Element.ALIGN_LEFT);
            rango.setSpacingAfter(15);
            document.add(rango);

            // Tabla
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{3, 3, 5, 3}); 

            agregarCeldaHeader(tabla, "Nombre Científico");
            agregarCeldaHeader(tabla, "Nombre Común");
            agregarCeldaHeader(tabla, "Descripción");
            agregarCeldaHeader(tabla, "Tipo de Especie");

            for (Especie e : especies) {
                tabla.addCell(e.getNombreCientifico());
                tabla.addCell(e.getNombreComun());
                tabla.addCell(e.getDescripcion() != null ? e.getDescripcion() : "");
                tabla.addCell(e.getTipoEspecie() != null ? e.getTipoEspecie() : "");
            }

            document.add(tabla);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    private void agregarCeldaHeader(PdfPTable tabla, String texto) {
        Font font = new Font(Font.HELVETICA, 12, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(CMYKColor.LIGHT_GRAY);
        tabla.addCell(cell);
    }

    @Override
    public byte[] generarReportePorTipo(String tipoEspecie) {

        List<Especie> especies = especieRepository.findByTipoEspecieIgnoreCase(tipoEspecie);

        if (especies.isEmpty()) {
            throw new RuntimeException("No hay especies del tipo: " + tipoEspecie);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            Font tituloFont = new Font(Font.HELVETICA, 20, Font.BOLD);

            Paragraph titulo = new Paragraph(
                "Reporte de Especies - Tipo: " + tipoEspecie.toUpperCase() + "\n\n",
                tituloFont
            );
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            for (Especie especie : especies) {

                Paragraph nombre = new Paragraph(
                        especie.getNombreCientifico(),
                        new Font(Font.HELVETICA, 16, Font.BOLD)
                );
                nombre.setSpacingAfter(10);
                document.add(nombre);

                PdfPTable tabla = new PdfPTable(2);
                tabla.setWidthPercentage(100);
                tabla.setSpacingAfter(15);

                agregarFila(tabla, "Nombre Científico:", especie.getNombreCientifico());
                agregarFila(tabla, "Nombre Común:", especie.getNombreComun());
                agregarFila(tabla, "Familia:", especie.getFamilia());
                agregarFila(tabla, "Descripción:", especie.getDescripcion());
                agregarFila(tabla, "Fecha de Registro:",
                        especie.getFechaRegistro() != null ? especie.getFechaRegistro().toString() : "No registrada");
                agregarFila(tabla, "Registrado por:",
                        especie.getRegistradoPor() != null ? especie.getRegistradoPor().toHexString() : "—");

                document.add(tabla);

                if (especie.getCaracteristicasMorfo() != null) {
                    Paragraph sub = new Paragraph("Características Morfológicas",
                            new Font(Font.HELVETICA, 14, Font.BOLD));
                    sub.setSpacingAfter(5);
                    document.add(sub);

                    PdfPTable morfo = new PdfPTable(2);
                    morfo.setWidthPercentage(100);

                    agregarFila(morfo, "Color:", especie.getCaracteristicasMorfo().getColor());
                    agregarFila(morfo, "Tamaño Alas:", especie.getCaracteristicasMorfo().getTamanoAlas());
                    agregarFila(morfo, "Forma Antenas:", especie.getCaracteristicasMorfo().getFormaAntenas());

                    morfo.setSpacingAfter(15);
                    document.add(morfo);
                }

                document.add(new Paragraph("-------------------------------------------------------------\n\n"));
            }

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    private void agregarFila(PdfPTable tabla, String label, String value) {
        Font bold = new Font(Font.HELVETICA, 12, Font.BOLD);
        tabla.addCell(new Phrase(label, bold));
        tabla.addCell(value != null ? value : "—");
    }
}
