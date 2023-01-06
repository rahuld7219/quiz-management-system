package com.qms.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qms.admin.dto.request.AddCategoryRequestDTO;
import com.qms.admin.dto.request.UpdateCategoryRequestDTO;
import com.qms.admin.dto.response.ResponseMessageDTO;
import com.qms.admin.service.CategoryService;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/category")
	public ResponseEntity<?> addCategory(@RequestBody AddCategoryRequestDTO addCategoryRequest) {
		ResponseMessageDTO response = categoryService.addCategory(addCategoryRequest);
		return ResponseEntity.status(response.getStatus()).body(response.getMessage()); // create standard response and
																						// add URI where created
	}

	@PatchMapping("/category")
	public ResponseEntity<?> updateCategory(@RequestBody UpdateCategoryRequestDTO updateCategoryRequest) {
		ResponseMessageDTO response = categoryService.updateCategory(updateCategoryRequest);
		return ResponseEntity.status(response.getStatus()).body(response.getMessage()); // create standard response and
																						// add URI where created
	}

	@DeleteMapping("/category")
	public ResponseEntity<?> deleteCategory(@RequestParam String categoryName) {
		ResponseMessageDTO response = categoryService.deleteCategory(categoryName);
		return ResponseEntity.status(response.getStatus()).body(response.getMessage()); // create standard response and
																						// add URI where created
	}

	@GetMapping("/category")
	public ResponseEntity<?> getCategory(@RequestParam String categoryName) {
		return ResponseEntity.ok().body(categoryService.listCategories());
	}
}
