package com.proyecto.proyecto.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.proyecto.proyecto.repository.IEspecieRepository;
import com.proyecto.proyecto.repository.IObservacionRepository;
import com.proyecto.proyecto.repository.IUbicacionRepository;

import com.proyecto.proyecto.repository.IUsuarioRepository;
@Service
public class BackupService {

    @Autowired
    private IEspecieRepository especieRepo;
    @Autowired
    private IObservacionRepository obsRepo;
    @Autowired
    private IUsuarioRepository usuarioRepo;
    @Autowired
    private IUbicacionRepository ubicRepo;

    public File generarBackup() throws IOException {
        File tempDir = new File("backup_temp");
        tempDir.mkdir();

        // Exportar cada colección a JSON
        exportarAJson(especieRepo.findAll(), "especies.json", tempDir);
        exportarAJson(obsRepo.findAll(), "observaciones.json", tempDir);
        exportarAJson(usuarioRepo.findAll(), "usuarios.json", tempDir);
        exportarAJson(ubicRepo.findAll(), "ubicaciones.json", tempDir);

        // Comprimir a ZIP
        File zipFile = new File("backup.zip");
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));

        for (File file : tempDir.listFiles()) {
            zipOut.putNextEntry(new ZipEntry(file.getName()));
            Files.copy(file.toPath(), zipOut);
            zipOut.closeEntry();
        }

        zipOut.close();

        // Limpiar temporales
        for (File file : tempDir.listFiles()) file.delete();
        tempDir.delete();

        return zipFile;
    }


    private void exportarAJson(Object data, String fileName, File folder) throws IOException {
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        File out = new File(folder, fileName);
        mapper.writeValue(out, data);
    }
}
