package com.qms.admin.service;

import java.util.List;

import com.qms.admin.dto.CategoryDTO;

public interface CategoryService {

	Long addCategory(final CategoryDTO categoryDTO);

	void updateCategory(final String categoryId, final CategoryDTO categoryDTO);

	void deleteCategory(final String categoryId);

	CategoryDTO getCategory(final String categoryId);

	List<CategoryDTO> listCategories();
}
