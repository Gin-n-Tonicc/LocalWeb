package com.localWeb.localWeb.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploadingService {
    String upload(MultipartFile[] multipartFiles) throws IOException;
}
