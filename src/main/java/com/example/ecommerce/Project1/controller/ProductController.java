package com.example.ecommerce.Project1.controller;

import com.example.ecommerce.Project1.config.AppConstant;
import com.example.ecommerce.Project1.payload.ProductDTO;
import com.example.ecommerce.Project1.payload.ProductResponse;
import com.example.ecommerce.Project1.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Represents the product controller component.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /**
     * Adds product.
     * @param productDTO the productDTO value.
     * @param categoryId the categoryId value.
     * @return the result of add product.
     */
    @PostMapping("/admin/categories/{categoryId}/product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long categoryId) {
        ProductDTO savedProductDTO = productService.addProduct(categoryId, productDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);

    }

    /**
     * Returns the all products.
     * @param pageNumber the pageNumber value.
     * @param pageSize the pageSize value.
     * @param sortBy the sortBy value.
     * @param sortOrder the sortOrder value.
     * @return the all products.
     */
    @GetMapping("/public/products")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder
    ) {
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    /**
     * Returns the product by category.
     * @param categoryId the categoryId value.
     * @param pageNumber the pageNumber value.
     * @param pageSize the pageSize value.
     * @param sortBy the sortBy value.
     * @param sortOrder the sortOrder value.
     * @return the product by category.
     */
    @GetMapping("/public/categories/{categoryId}/product")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ProductResponse> getProductByCategory(
            @PathVariable Long categoryId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstant.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstant.SORT_DIR, required = false) String sortOrder) {
        ProductResponse productResponse = productService.searchByCategory(categoryId,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    /**
     * Returns the product by keyword.
     * @param keyword the keyword value.
     * @param pageNumber the pageNumber value.
     * @param pageSize the pageSize value.
     * @param sortBy the sortBy value.
     * @param sortOrder the sortOrder value.
     * @return the product by keyword.
     */
    @GetMapping("/public/products/keyword/{keyword}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ProductResponse> getProductByKeyword(
            @PathVariable String keyword,
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name="sortBy",defaultValue = AppConstant.SORT_PRODUCTS_BY,required = false) String sortBy ,
            @RequestParam(name="sortOrder",defaultValue = AppConstant.SORT_DIR,required = false) String sortOrder) {
        ProductResponse productResponse = productService.searchProductByKeyword(keyword,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    /**
     * Executes up date product.
     * @param productDTO the productDTO value.
     * @param productId the productId value.
     * @return the result of up date product.
     */
    @PutMapping("/admin/products/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long productId) {

        ProductDTO updatedProductDTO = productService.updateProduct(productId, productDTO);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    /**
     * Deletes product.
     * @param productId the productId value.
     * @return the result of delete product.
     */
    @DeleteMapping("/admin/products/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        ProductDTO deletedProductDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProductDTO, HttpStatus.OK);
    }

    /**
     * Updates product image.
     * @param productId the productId value.
     * @param image the image value.
     * @return the result of update product image.
     * @throws IOException if the operation cannot be completed.
     */
    @PutMapping("/products/{productId}/image")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId, @RequestParam("image") MultipartFile image) throws IOException {

        ProductDTO updatedProductDTO = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }
}

