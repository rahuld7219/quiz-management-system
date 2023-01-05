package com.qms.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.qms.admin.dto.AddCategoryRequestDTO;
import com.qms.admin.dto.ResponseMessageDTO;
import com.qms.admin.model.Category;
import com.qms.admin.repository.CategoryRepository;
import com.qms.admin.service.CategoryService;

@Service
public class CategoryServiceimpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public ResponseMessageDTO addCategory(AddCategoryRequestDTO addCategoryRequest) {
		Category category = new Category();
		category.setName(addCategoryRequest.getName());
		categoryRepository.save(category);
		return new ResponseMessageDTO(HttpStatus.CREATED, "Category save Successful.");
	}

}
