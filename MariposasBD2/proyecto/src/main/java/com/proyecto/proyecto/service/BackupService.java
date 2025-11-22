package com.proyecto.proyecto.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.proyecto.proyecto.model.Especie;
import com.proyecto.proyecto.model.Observacion;
import com.proyecto.proyecto.model.Ubicacion;
import com.proyecto.proyecto.model.Usuario;
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

    private final ObjectMapper mapper = new ObjectMapper();

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

    public void restaurar(MultipartFile file) throws Exception {

        File tempDir = new File("restore_temp"); 
        if (!tempDir.exists()) tempDir.mkdir();

        File zipFile = new File(tempDir, "backup.zip");
        file.transferTo(zipFile);

        unzip(zipFile.getAbsolutePath(), tempDir.getAbsolutePath());

        restaurarEspecies(new File(tempDir, "especies.json"));
        restaurarObservaciones(new File(tempDir, "observaciones.json"));
        restaurarUsuarios(new File(tempDir, "usuarios.json"));
        restaurarUbicaciones(new File(tempDir, "ubicaciones.json"));

        for (File f : tempDir.listFiles()) f.delete();
        tempDir.delete();
    }

    private void restaurarEspecies(File jsonFile) throws IOException {
        if (!jsonFile.exists()) return;

        List<Especie> lista = mapper.readValue(jsonFile, new TypeReference<List<Especie>>() {});
        especieRepo.deleteAll();
        especieRepo.saveAll(lista);
    }

    private void restaurarObservaciones(File jsonFile) throws IOException {
        if (!jsonFile.exists()) return;

        List<Observacion> lista = mapper.readValue(jsonFile, new TypeReference<List<Observacion>>() {});
        obsRepo.deleteAll();
        obsRepo.saveAll(lista);
    }

    private void restaurarUsuarios(File jsonFile) throws IOException {
        if (!jsonFile.exists()) return;

        List<Usuario> lista = mapper.readValue(jsonFile, new TypeReference<List<Usuario>>() {});
        usuarioRepo.deleteAll();
        usuarioRepo.saveAll(lista);
    }

    private void restaurarUbicaciones(File jsonFile) throws IOException {
        if (!jsonFile.exists()) return;

        List<Ubicacion> lista = mapper.readValue(jsonFile, new TypeReference<List<Ubicacion>>() {});
        ubicRepo.deleteAll();
        ubicRepo.saveAll(lista); 
    }

    private void unzip(String zipPath, String destDir) throws IOException {
        File dir = new File(destDir);

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath));
        ZipEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {
            File newFile = new File(dir, zipEntry.getName());

            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

            fos.close();
            zipEntry = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();
    }
}
