package com.example.file_uploader.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    // Diretório onde os arquivos serão salvos
    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Verifica se o diretório existe, se não, cria
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Salva o arquivo no diretório especificado
            Path filePath = Paths.get(uploadDir + File.separator + file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath);

            return ResponseEntity.status(HttpStatus.OK).body("Upload realizado com sucesso: " + file.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao realizar o upload do arquivo: " + file.getOriginalFilename());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() {
        File directory = new File(uploadDir);
        List<String> fileList = new ArrayList<>();

        // Lista os arquivos no diretório
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    fileList.add(file.getName());
                }
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(fileList);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDir + File.separator + fileName);
            byte[] fileContent = Files.readAllBytes(filePath);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
