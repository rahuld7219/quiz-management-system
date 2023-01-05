package com.qms.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qms.admin.dto.AddCategoryRequestDTO;
import com.qms.admin.dto.ResponseMessageDTO;
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
																						// // add URI where created
	}
}
