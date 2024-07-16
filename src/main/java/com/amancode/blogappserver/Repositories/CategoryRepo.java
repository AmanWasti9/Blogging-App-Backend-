package com.amancode.blogappserver.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amancode.blogappserver.Entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

	
	
}
