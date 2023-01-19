package com.qms.admin.service;

import com.qms.admin.dto.request.CategoryRequest;
import com.qms.admin.dto.response.CategoryResponse;
import com.qms.admin.dto.response.ListCategoryResponse;

public interface CategoryService {

	CategoryResponse addCategory(final CategoryRequest categoryRequest);

	CategoryResponse updateCategory(final Long categoryId, final CategoryRequest categoryRequest);

	void deleteCategory(final Long categoryId);

	CategoryResponse getCategory(final Long categoryId);

	ListCategoryResponse getCategoryList();
}
