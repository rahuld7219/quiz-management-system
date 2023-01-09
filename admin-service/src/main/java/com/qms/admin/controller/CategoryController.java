package com.qms.admin.controller;

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

import com.qms.admin.dto.request.AddCategoryRequestDTO;
import com.qms.admin.dto.request.UpdateCategoryRequestDTO;
import com.qms.admin.dto.response.ResponseMessageDTO;
import com.qms.admin.service.CategoryService;

@RestController
@RequestMapping("/api/v1/admin")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/category")
	public ResponseEntity<?> addCategory(@RequestBody final AddCategoryRequestDTO addCategoryRequest) {
		ResponseMessageDTO response = categoryService.addCategory(addCategoryRequest);
//		URI location = new URI("/category/"+ id);
//		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/category").toUriString());
		return ResponseEntity.status(response.getStatus()).body(response.getMessage()); // create standard response and
																						// add URI where created
	}

	@PutMapping("/category/{categoryId}") // TODO: can use uuid in here?
	public ResponseEntity<?> updateCategory(@PathVariable final String categoryId,
			@RequestBody final UpdateCategoryRequestDTO updateCategoryRequest) {
		ResponseMessageDTO response = categoryService.updateCategory(categoryId, updateCategoryRequest);
		return ResponseEntity.status(response.getStatus()).body(response.getMessage()); // create standard response and
																						// add URI where created
	}

	@DeleteMapping("/category/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable final String categoryId) {
		ResponseMessageDTO response = categoryService.deleteCategory(categoryId);
		return ResponseEntity.status(response.getStatus()).body(response.getMessage()); // create standard response and
																						// add URI where created
	}

	@GetMapping(value = { "/category/{categoryId}", "/category" })
	public ResponseEntity<?> getCategory(@PathVariable final Optional<String> categoryId) {
		if (categoryId.isPresent()) {
			return ResponseEntity.ok().body(categoryService.getCategory(categoryId.get()));
		}
		return ResponseEntity.ok().body(categoryService.listCategories());
	}

}
