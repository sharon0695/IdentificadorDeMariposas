package com.proyecto.proyecto.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.proyecto.service.BackupService;

@RestController
@RequestMapping("/api/backup")
public class BackupController {
    @Autowired private BackupService backupService;
    
    @GetMapping("/backup")
    public ResponseEntity<Resource> generarBackup() throws IOException {
        File zip = backupService.generarBackup();

        InputStreamResource resource = new InputStreamResource(new FileInputStream(zip));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=backup.zip")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping("/restore")
    public ResponseEntity<String> restaurarBackup(@RequestParam("file") MultipartFile file) throws Exception {
        backupService.restaurar(file);
        return ResponseEntity.ok("Backup restaurado correctamente");
    }
}
