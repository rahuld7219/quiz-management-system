package com.qms.admin.service;

import com.qms.admin.dto.AddCategoryRequestDTO;
import com.qms.admin.dto.ResponseMessageDTO;

public interface CategoryService {

	ResponseMessageDTO addCategory(AddCategoryRequestDTO addCategoryRequest);
//	updateCategory();
//	deleteCategory();
//	getCategory();
//	listCategories();
}
