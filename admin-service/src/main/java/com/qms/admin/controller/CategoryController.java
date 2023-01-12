package com.qms.admin.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qms.admin.dto.CategoryDTO;
import com.qms.admin.service.CategoryService;

@RestController
@RequestMapping("/api/v1/admin")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/category")
	public ResponseEntity<String> addCategory(@RequestBody final CategoryDTO categoryDTO) {
		Long id = categoryService.addCategory(categoryDTO);
//		URI location = new URI("/category/"+ id);
		URI location = URI.create("/api/v1/admin/category/" + id);
		return ResponseEntity.created(location).body("Category created successfully.");
	}

	@PutMapping("/category/{categoryId}") // TODO: can use uuid in here?
	public ResponseEntity<String> updateCategory(@PathVariable final String categoryId,
			@RequestBody final CategoryDTO categoryDTO) {
		categoryService.updateCategory(categoryId, categoryDTO);
		return ResponseEntity.ok("Category updated successfully.");
	}

	@DeleteMapping("/category/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable final String categoryId) {
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok("Category deleted Successfully.");
	}

	@GetMapping(value = { "/category/{categoryId}", "/category" })
	public ResponseEntity<?> getCategory(@PathVariable final Optional<String> categoryId) {
		if (categoryId.isPresent()) {
			return ResponseEntity.ok().body(categoryService.getCategory(categoryId.get()));
		}
		return ResponseEntity.ok().body(categoryService.listCategories());
	}

}
