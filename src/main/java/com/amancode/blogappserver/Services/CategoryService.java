package com.amancode.blogappserver.Services;

import java.util.List;

import com.amancode.blogappserver.Payloads.CategoryDTO;

public interface CategoryService {

	//Create
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    
    //Update
    CategoryDTO updateCategory(CategoryDTO categoryDTO,Integer categoryId);
    
    //Get Category Id
    CategoryDTO getCategoryById(Integer categoryId);
    
    //Get All Categories
    List<CategoryDTO> getAllCategories();
    
    //Delete Category
    void deleteCategory(Integer categoryId);
	
	
	
}
