package com.example.ecommerce.Project1.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Represents the file service impl component.
 */
@Service
public class FileServiceImpl  implements  FileService {
    /**
     * Uploads image.
     * @param path the path value.
     * @param file the file value.
     * @return the result of upload image.
     * @throws IOException if the operation cannot be completed.
     */
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        assert originalFilename != null;
        String fileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
        String filePath = path+ File.separator+fileName;

        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}
