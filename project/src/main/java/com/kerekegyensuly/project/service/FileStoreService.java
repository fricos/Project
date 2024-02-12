package com.kerekegyensuly.project.service;

import com.kerekegyensuly.project.config.StorageProperties;
import com.kerekegyensuly.project.exceptions.StorageException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStoreService {
    private StorageProperties properties = new StorageProperties();
    Path rootLocation = Paths.get(properties.getLocation());

    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("A tarolo ures.");
            }

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String uploadedFileName = UUID.randomUUID().toString() + "." + extension;

            Path destinationFile = rootLocation.resolve(
                    Paths.get(uploadedFileName))
                    .normalize().toAbsolutePath();

            try (InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);

                final String baseUrl =
                        ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

                return baseUrl+"/fileUpload/files/"+uploadedFileName;
            }
        }
        catch (IOException e) {
            throw new StorageException("Nem sikerult eltarolni az adatot.", e);
        }
    }

    public Resource load(String filename) {
        try{
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()){
                return resource;
            }else {
                throw new RuntimeException("Nem lehet beolvasni a fajlt!");
            }
        }catch (MalformedURLException e){
            throw new RuntimeException("Hiba : " + e.getMessage());
        }
    }
}