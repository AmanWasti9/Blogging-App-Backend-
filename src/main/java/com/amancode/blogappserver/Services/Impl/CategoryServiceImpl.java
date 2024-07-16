package com.amancode.blogappserver.Services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amancode.blogappserver.Entities.Category;
import com.amancode.blogappserver.Exceptions.ResourceNotFoundException;
import com.amancode.blogappserver.Payloads.CategoryDTO;
import com.amancode.blogappserver.Repositories.CategoryRepo;
import com.amancode.blogappserver.Services.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category cat = this.modelMapper.map(categoryDTO, Category.class);
		Category savedCategory = this.categoryRepo.save(cat);
		return this.modelMapper.map(savedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		category.setCategoryTitle(categoryDTO.getCategoryTitle());

		Category updatedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO getCategoryById(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		return this.modelMapper.map(category, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAllCategories() {

		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDTO> categoryDTOs = categories.stream().map(cat -> this.modelMapper.map(cat, CategoryDTO.class))
				.collect(Collectors.toList());

		return categoryDTOs;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		this.categoryRepo.delete(category);

	}

}
