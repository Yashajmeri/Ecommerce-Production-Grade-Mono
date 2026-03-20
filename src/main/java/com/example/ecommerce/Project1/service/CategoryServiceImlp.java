package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.exception.APIException;
import com.example.ecommerce.Project1.exception.ResourceNotFoundException;
import com.example.ecommerce.Project1.model.Category;
import com.example.ecommerce.Project1.payload.CategoryDTO;
import com.example.ecommerce.Project1.payload.CategoryResponse;
import com.example.ecommerce.Project1.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Represents the category service imlp component.
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImlp  implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    /**
     * Returns the all categories.
     * @param pageNumber the pageNumber value.
     * @param pageSize the pageSize value.
     * @param sortBy the sortBy value.
     * @param sortOrder the sortOrder value.
     * @return the all categories.
     */
    @Override
    public CategoryResponse getAllCategories( Integer pageNumber , Integer pageSize,String sortBy,String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByAndOrder); // used Factory method
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories =  categoryPage.getContent();

               if (categories.isEmpty())
                   throw new APIException("No category created till now !");
               List<CategoryDTO> categoryDTO = categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
               CategoryResponse categoryResponse = new CategoryResponse();
               categoryResponse.setContent(categoryDTO);
               categoryResponse.setPageNumber(categoryPage.getNumber());
               categoryResponse.setPageSize(categoryPage.getSize());
               categoryResponse.setTotalElements(categoryPage.getTotalElements());
               categoryResponse.setTotalPages((long) categoryPage.getTotalPages());
               categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    /**
     * Creates category.
     * @param categoryDTO the categoryDTO value.
     * @return the result of create category.
     */
    @Override
    public CategoryDTO CreateCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);

     Category savedCategoryfromDB=categoryRepository.findByCategoryName(category.getCategoryName());
     if(savedCategoryfromDB !=null) throw new APIException("Category with this name "+category.getCategoryName()+" already exists!!");
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);

    }

    /**
     * Deletes category.
     * @param categoryId the categoryId value.
     * @return the result of delete category.
     */
    @Override
    public CategoryDTO DeleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
         categoryRepository.delete(category);

        return modelMapper.map(category, CategoryDTO.class);
    }

    /**
     * Updates category.
     * @param categoryDTO the categoryDTO value.
     * @param categoryId the categoryId value.
     * @return the result of update category.
     */
    @Override
    public CategoryDTO UpdateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category  = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
           category.setCategoryId(categoryId);
           savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}

