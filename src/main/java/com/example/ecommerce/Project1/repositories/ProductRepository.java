package com.example.ecommerce.Project1.repositories;

import com.example.ecommerce.Project1.model.Category;
import com.example.ecommerce.Project1.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Defines the contract for product repository operations.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finds by category order by price asc.
     * @param category the category value.
     * @param pageDetails the pageDetails value.
     * @return the result of find by category order by price asc.
     */
    Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails);

    /**
     * Finds by product name like ignore case.
     * @param keyword the keyword value.
     * @param pageDetails the pageDetails value.
     * @return the result of find by product name like ignore case.
     */
    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);
}
