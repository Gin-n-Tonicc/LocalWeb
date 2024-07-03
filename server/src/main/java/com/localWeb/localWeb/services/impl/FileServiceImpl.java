package com.localWeb.localWeb.services.impl;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.localWeb.localWeb.enums.FileType;
import com.localWeb.localWeb.exceptions.files.UnsupportedFileTypeException;
import com.localWeb.localWeb.repositories.FileRepository;
import com.localWeb.localWeb.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final MessageSource messageSource;

    @Override
    public List<com.localWeb.localWeb.models.entity.File> upload(MultipartFile[] multipartFiles) throws IOException {
        List<com.localWeb.localWeb.models.entity.File> savedFiles = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();
            assert fileName != null;
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

            File file = this.convertToFile(multipartFile, fileName);

            savedFiles.add(this.uploadFile(file, fileName));
        }
        return savedFiles;
    }

    @Override
    public com.localWeb.localWeb.models.entity.File uploadFile(File file, String fileName) throws IOException {
        String extension = getExtension(fileName);
        System.out.println("EXTENSION " + extension);
        if (!FileType.isSupportedExtension(extension) && !(extension.startsWith(".com"))) {
            throw new UnsupportedFileTypeException(messageSource);
        }

        BlobId blobId = BlobId.of("localweb-428009.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = FileServiceImpl.class.getClassLoader().getResourceAsStream("firebase.json");

        assert inputStream != null;
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/localweb-428009.appspot.com/o/%s?alt=media";
        String path = String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));

        return saveFileToDatabase(file, fileName, path, extension);
    }

    @Override
    public File downloadImageFromURL(String imageUrl) throws Exception {
        URI uri = new URI(imageUrl);
        Resource resource = new UrlResource(uri);

        File tempFile = File.createTempFile("downloaded-", ".jpg");
        try (InputStream inputStream = resource.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return tempFile;
    }

    @Override
    public File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private com.localWeb.localWeb.models.entity.File saveFileToDatabase(File file, String fileName, String path, String extension) throws IOException {
        com.localWeb.localWeb.models.entity.File fileUpload = new com.localWeb.localWeb.models.entity.File();
        fileUpload.setName(fileName);
        fileUpload.setPath(path);
        fileUpload.setType(extension);
        fileUpload.setSize(Files.size(file.toPath()));
        return fileRepository.save(fileUpload);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}