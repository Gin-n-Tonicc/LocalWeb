package com.localWeb.localWeb.services;

import com.localWeb.localWeb.models.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    List<File> upload(MultipartFile[] multipartFiles) throws IOException;

    File uploadFile(java.io.File file, String fileName) throws IOException;

    java.io.File downloadImageFromURL(String imageUrl) throws Exception;

    java.io.File convertToFile(MultipartFile multipartFile, String fileName) throws Exception;
}
