package com.qms.admin.service;

import java.util.List;

import com.qms.admin.dto.request.CategoryRequest;
import com.qms.admin.dto.response.CategoryResponse;

public interface CategoryService {

	CategoryResponse addCategory(final CategoryRequest categoryDTO);

	CategoryResponse updateCategory(final String categoryId, final CategoryRequest categoryDTO);

	void deleteCategory(final String categoryId);

	CategoryRequest getCategory(final String categoryId);

	List<CategoryRequest> listCategories();
}
