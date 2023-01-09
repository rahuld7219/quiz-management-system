package com.qms.admin.service;

import java.util.List;

import com.qms.admin.dto.request.AddCategoryRequestDTO;
import com.qms.admin.dto.request.UpdateCategoryRequestDTO;
import com.qms.admin.dto.response.GetCategoryResponseDTO;
import com.qms.admin.dto.response.ResponseMessageDTO;

public interface CategoryService {

	ResponseMessageDTO addCategory(AddCategoryRequestDTO addCategoryRequest);

	ResponseMessageDTO updateCategory(String categoryId, UpdateCategoryRequestDTO updateCategoryRequest);

	ResponseMessageDTO deleteCategory(String categoryId);

	GetCategoryResponseDTO getCategory(String categoryId);

	List<GetCategoryResponseDTO> listCategories();
}
