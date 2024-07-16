package com.amancode.blogappserver.Payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
	
	private Integer categoryId;
	
	@NotEmpty
	@Size(min=4, message = "Title must be of min 4 characters")
	private String categoryTitle;

	private String categoryIcon;
}
