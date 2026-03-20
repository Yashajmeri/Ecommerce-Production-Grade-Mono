package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.exception.APIException;
import com.example.ecommerce.Project1.exception.ResourceNotFoundException;
import com.example.ecommerce.Project1.model.Cart;
import com.example.ecommerce.Project1.model.Category;
import com.example.ecommerce.Project1.model.Product;
import com.example.ecommerce.Project1.payload.CartDTO;
import com.example.ecommerce.Project1.payload.ProductDTO;
import com.example.ecommerce.Project1.payload.ProductResponse;
import com.example.ecommerce.Project1.repositories.CartRepository;
import com.example.ecommerce.Project1.repositories.CategoryRepository;
import com.example.ecommerce.Project1.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import java.util.List;
import java.util.stream.Stream;


/**
 * Represents the product service impl component.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;
    @Value("${project.image}")
    private String path;
    private final CartRepository cartRepository;
    private final CartService cartService;
    /**
     * Adds product.
     * @param categoryId the categoryId value.
     * @param productDTO the productDTO value.
     * @return the result of add product.
     */
    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "CategoryId", categoryId)
        );
        boolean isProductNotPresent = true;
        List<Product> products = category.getProducts();
        for (Product product : products) {
            if (productDTO.getProductName().equals(product.getProductName())) {
                isProductNotPresent = false;
                break;
            }
        }
        if (isProductNotPresent) {
            Product product = modelMapper.map(productDTO, Product.class);
            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        } else {
            throw new APIException("Product already exists!!");
        }


    }

    /**
     * Returns the all products.
     * @param pageNumber the pageNumber value.
     * @param pageSize the pageSize value.
     * @param sortBy the sortBy value.
     * @param sortOrder the sortOrder value.
     * @return the all products.
     */
    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder); // used Factory method
        Page<Product> productPage = productRepository.findAll(pageDetails);

        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages((long) productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

    /**
     * Searches by category.
     * @param categoryId the categoryId value.
     * @param pageNumber the pageNumber value.
     * @param pageSize the pageSize value.
     * @param sortBy the sortBy value.
     * @param sortOrder the sortOrder value.
     * @return the result of search by category.
     */
    @Override
    public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "CategoryId", categoryId));
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder); // used Factory method
        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category,pageDetails);

        List<Product> products = productPage.getContent();
        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages((long) productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;
    }

    /**
     * Searches by product by keyword.
     * @param keyword the keyword value.
     * @param pageNumber the pageNumber value.
     * @param pageSize the pageSize value.
     * @param sortBy the sortBy value.
     * @param sortOrder the sortOrder value.
     * @return the result of search by product by keyword.
     */
    @Override
    public ProductResponse searchByProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder); // used Factory method
        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%',pageDetails);

        List<Product> products = productPage.getContent();
        if (products.isEmpty()) {
            throw new APIException("No products found!");
        }
        List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages(productPage.getTotalElements());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());
        return productResponse;
    }

    /**
     * Executes up date product.
     * @param productId the productId value.
     * @param productDTO the productDTO value.
     * @return the result of up date product.
     */
    @Override
    public ProductDTO upDateProduct(Long productId, ProductDTO productDTO) {
        Product productFromDB = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product", "product Id", productId));
        Product product = modelMapper.map(productDTO, Product.class);
        productFromDB.setProductName(product.getProductName());
        productFromDB.setPrice(product.getPrice());
        productFromDB.setDiscount(product.getDiscount());
        productFromDB.setSpecialPrice(product.getSpecialPrice());
        productFromDB.setDescription(product.getDescription());
        productFromDB.setQuantity(product.getQuantity());
        Product SavedProduct = productRepository.save(productFromDB);

        List<Cart> carts = cartRepository.findCartsByProductId(productId);
        List<CartDTO> cartDTOs = carts.stream()
                .map(cart-> {
                    CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
                    List<ProductDTO> products = cart.getCartItems().stream()
                            .map(p-> modelMapper.map(p, ProductDTO.class)).toList();
                    cartDTO.setProducts(products);
                    return cartDTO;
                }).toList();
        cartDTOs.forEach(cart->cartService.updateProductInCarts(cart.getCartId(),productId));

        return modelMapper.map(SavedProduct, ProductDTO.class);
    }

    /**
     * Deletes product.
     * @param productId the productId value.
     * @return the result of delete product.
     */
    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "productId", productId)
        );
        List<Cart> carts = cartRepository.findCartsByProductId(productId);
        carts.forEach(cart->cartService.deleteProductFromCart(cart.getCartId(),productId));
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    /**
     * Executes up date product image.
     * @param productId the productId value.
     * @param image the image value.
     * @return the result of up date product image.
     * @throws IOException if the operation cannot be completed.
     */
    @Override
    public ProductDTO upDateProductImage(Long productId, MultipartFile image) throws IOException {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "productId", productId)
        );

        String fileName = fileService.uploadImage(path, image);
        product.setImage(fileName);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

}
