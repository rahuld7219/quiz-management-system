package com.qms.admin.controller;

import java.net.URI;
import java.time.LocalDateTime;

import javax.validation.Valid;

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

import com.qms.admin.constant.AdminMessageConstant;
import com.qms.admin.constant.AdminURIConstant;
import com.qms.admin.dto.request.CategoryRequest;
import com.qms.admin.dto.response.CategoryResponse;
import com.qms.admin.service.CategoryService;
import com.qms.common.dto.response.ApiResponse;

@RestController
@RequestMapping(AdminURIConstant.BASE_ADMIN_URL + AdminURIConstant.CATEGORY_URL)
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping()
	public ResponseEntity<ApiResponse> addCategory(@Valid @RequestBody final CategoryRequest categoryRequest) {

		CategoryResponse response = categoryService.addCategory(categoryRequest);
		URI location = URI.create(
				AdminURIConstant.BASE_ADMIN_URL + AdminURIConstant.CATEGORY_URL + "/" + response.getData().getId());

		return ResponseEntity.created(location).body(response);
	}

	@PutMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable final Long categoryId,
			@Valid @RequestBody final CategoryRequest categoryRequest) {

		return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryRequest));
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable final Long categoryId) {
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok(new ApiResponse().setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.CATGEORY_DELETED).setResponseTime(LocalDateTime.now()));
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> getCategory(@PathVariable final Long categoryId) {
		return ResponseEntity.ok(categoryService.getCategory(categoryId));
	}

	@GetMapping()
	public ResponseEntity<ApiResponse> getCategoryList() {
		return ResponseEntity.ok(categoryService.getCategoryList());
	}
}
