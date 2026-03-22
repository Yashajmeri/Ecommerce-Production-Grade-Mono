package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.payload.ProductDTO;
import com.example.ecommerce.Project1.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Defines the contract for product service operations.
 */
public interface ProductService {
    /**
     * Adds product.
     * @param categoryId the categoryId value.
     * @param productDTO the productDTO value.
     * @return the result of add product.
     */
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    /**
     * Returns the all products.
     * @param pageNumber the pageNumber value.
     * @param pageSize the pageSize value.
     * @param sortBy the sortBy value.
     * @param sortOrder the sortOrder value.
     * @return the all products.
     */
    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    /**
     * Searches by category.
     * @param categoryId the categoryId value.
     * @param pageNumber the pageNumber value.
     * @param pageSize the pageSize value.
     * @param sortBy the sortBy value.
     * @param sortOrder the sortOrder value.
     * @return the result of search by category.
     */
    ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    /**
     * Searches by product by keyword.
     * @param keyword the keyword value.
     * @param pageNumber the pageNumber value.
     * @param pageSize the pageSize value.
     * @param sortBy the sortBy value.
     * @param sortOrder the sortOrder value.
     * @return the result of search by product by keyword.
     */
    ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    /**
     * Executes up date product.
     * @param productId the productId value.
     * @param productDTO the productDTO value.
     * @return the result of up date product.
     */
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    /**
     * Deletes product.
     * @param productId the productId value.
     * @return the result of delete product.
     */
    ProductDTO deleteProduct(Long productId);

    /**
     * Updates product image.
     * @param productId the productId value.
     * @param image the image value.
     * @return the result of update product image.
     * @throws IOException if the operation cannot be completed.
     */
    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
