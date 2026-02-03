package com.example.ecommerce.Project1.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl  implements  FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // get the file name of the original file
        String originalFilename = file.getOriginalFilename();
        // We will generate the unique file name
        String randomId = UUID.randomUUID().toString();
        //        assert originalFilename != null;
        assert originalFilename != null;
        String fileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
        String filePath = path+ File.separator+fileName;


        // check if file exist
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        // upload  to the server
        Files.copy(file.getInputStream(), Paths.get(filePath));
        // return the file  name
        return fileName;
    }
}
