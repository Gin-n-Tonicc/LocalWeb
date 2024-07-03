package com.localWeb.localWeb.controllers;

import com.localWeb.localWeb.services.ImageUploadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class ImageController {

    private final ImageUploadingService imageUploadingService;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile[] multipartFile) throws IOException {
        return imageUploadingService.upload(multipartFile);
    }
}