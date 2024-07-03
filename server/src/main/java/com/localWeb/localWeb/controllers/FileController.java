package com.localWeb.localWeb.controllers;

import com.localWeb.localWeb.models.entity.File;
import com.localWeb.localWeb.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public List<File> upload(@RequestParam("file") MultipartFile[] multipartFile) throws IOException {
        return fileService.upload(multipartFile);
    }
}