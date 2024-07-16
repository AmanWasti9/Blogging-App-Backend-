package com.amancode.blogappserver.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amancode.blogappserver.Payloads.API_Response;
import com.amancode.blogappserver.Payloads.CategoryDTO;
import com.amancode.blogappserver.Services.CategoryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@SecurityRequirement(name = "scheme1")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	public CategoryService categoryService;

	// POST - create user
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO createdCategoryDTO = this.categoryService.createCategory(categoryDTO);
		return new ResponseEntity<CategoryDTO>(createdCategoryDTO, HttpStatus.CREATED);
	}

	// PUT - update user
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
			@PathVariable("categoryId") Integer cid) {
		CategoryDTO updatedCategory = this.categoryService.updateCategory(categoryDTO, cid);
		return new ResponseEntity<CategoryDTO>(updatedCategory, HttpStatus.OK);
	}

	// DELETE - delete user
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<API_Response> deleteCategory(@PathVariable("categoryId") Integer cid) {
		this.categoryService.deleteCategory(cid);
		return new ResponseEntity<API_Response>(new API_Response("Category Deleted Successfully", true), HttpStatus.OK);
	}

	// GET - get All users
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getAllCategory() {
		return ResponseEntity.ok(this.categoryService.getAllCategories());
	}

	// GET - get user by id
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getSingleCategory(@PathVariable Integer categoryId) {
		return ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));
	}

}
