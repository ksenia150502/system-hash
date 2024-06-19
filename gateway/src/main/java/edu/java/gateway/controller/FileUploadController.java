package edu.java.gateway.controller;

import edu.java.gateway.entity.Url;
import edu.java.gateway.service.FileUploadService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController implements FileUploadApi {
    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping(path = "/api/v1/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Url uploadFile(@RequestParam("file") @Parameter(name="file", description = "Файл с хэш-функцией") MultipartFile file,
                          @RequestParam("data") @Parameter(name = "data", description = "Файл с данными для хэширования") MultipartFile data) {
        return fileUploadService.saveNewFileWithData(file, data);
    }
}