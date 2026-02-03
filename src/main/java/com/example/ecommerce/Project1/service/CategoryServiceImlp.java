package com.example.ecommerce.Project1.service;

import com.example.ecommerce.Project1.exception.APIException;
import com.example.ecommerce.Project1.exception.ResourceNotFoundException;
import com.example.ecommerce.Project1.model.Category;
import com.example.ecommerce.Project1.payload.CategoryDTO;
import com.example.ecommerce.Project1.payload.CategoryResponse;
import com.example.ecommerce.Project1.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImlp  implements CategoryService{
//    List<Category> categories = new ArrayList<Category>();
//    private Long nextId=1L;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
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

    @Override
    public CategoryDTO CreateCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);

     Category savedCategoryfromDB=categoryRepository.findByCategoryName(category.getCategoryName());
     if(savedCategoryfromDB !=null) throw new APIException("Category with this name "+category.getCategoryName()+" already exists!!");
//        category.setCategoryId(nextId++)
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);

    }

    @Override
    public CategoryDTO DeleteCategory(Long categoryId) {
//        List<Category> categories = categoryRepository.findAll();
//         Category category = categories.stream().filter(c-> {
//             return c.getCategoryId().equals(categoryId);
//         }).findFirst().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found!!"));

       // this is Using ResponseStatusException
//       Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found!!"));
         // This  is Using Custom ExceptionHandle or we can say global ExceptionHandler
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
         categoryRepository.delete(category);

        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO UpdateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category  = modelMapper.map(categoryDTO, Category.class);
        // This  is Using Custom ExceptionHandler ,or we can say global ExceptionHandler
        Category savedCategory = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
           category.setCategoryId(categoryId);
           savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}

