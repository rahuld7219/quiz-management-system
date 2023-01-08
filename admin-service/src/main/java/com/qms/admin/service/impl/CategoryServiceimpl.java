package com.qms.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.qms.admin.dto.request.AddCategoryRequestDTO;
import com.qms.admin.dto.request.UpdateCategoryRequestDTO;
import com.qms.admin.dto.response.GetCategoryResponseDTO;
import com.qms.admin.dto.response.ResponseMessageDTO;
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

	@Override
	public ResponseMessageDTO updateCategory(UpdateCategoryRequestDTO updateCategoryRequest) {
		Optional<Category> category = categoryRepository.findByName(updateCategoryRequest.getName());

		if (!category.isPresent()) {
			throw new RuntimeException("Category not exist."); // TODO: make custom exception
		}

		category.get().setName(updateCategoryRequest.getName());

		categoryRepository.save(category.get());

		return new ResponseMessageDTO(HttpStatus.OK, "Category update successful.");

	}

	@Override
	public ResponseMessageDTO deleteCategory(String categoryName) {
		Optional<Category> category = categoryRepository.findByName(categoryName);
		if (!category.isPresent()) {
			throw new RuntimeException("Category not exist."); // TODO: make custom exception
		}
		categoryRepository.delete(category.get());
		return new ResponseMessageDTO(HttpStatus.OK, "Category delete successful.");
	}

	@Override
	public GetCategoryResponseDTO getCategory(String categoryName) {
		Optional<Category> category = categoryRepository.findByName(categoryName);
		if (!category.isPresent()) {
			throw new RuntimeException("Category not exist."); // TODO: make custom exception
		}
		GetCategoryResponseDTO getCatgeoryResponse = new GetCategoryResponseDTO(category.get().getName(),
				category.get().getCreatedOn(), category.get().getUpdatedOn());
		return getCatgeoryResponse;
	}

	@Override
	public List<GetCategoryResponseDTO> listCategories() {
		List<Category> categories = categoryRepository.findAll();
		List<GetCategoryResponseDTO> categoriesResponseDTO = new ArrayList<>();
		categories.forEach(category -> categoriesResponseDTO
				.add(new GetCategoryResponseDTO(category.getName(), category.getCreatedOn(), category.getUpdatedOn())));

		return categoriesResponseDTO;
	}

}
