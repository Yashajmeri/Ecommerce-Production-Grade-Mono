package com.example.ecommerce.Project1.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Defines the contract for file service operations.
 */
public interface FileService {
   /**
    * Uploads image.
    * @param path the path value.
    * @param file the file value.
    * @return the result of upload image.
    * @throws IOException if the operation cannot be completed.
    */
   String uploadImage(String path ,MultipartFile file) throws IOException;
}
