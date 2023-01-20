package com.qms.admin.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.qms.admin.constant.AdminMessageConstant;
import com.qms.admin.dto.CategoryDTO;
import com.qms.admin.dto.request.CategoryRequest;
import com.qms.admin.dto.response.CategoryResponse;
import com.qms.admin.dto.response.ListCategoryResponse;
import com.qms.admin.exception.custom.CategoryConstraintViolationException;
import com.qms.admin.exception.custom.CategoryNotExistException;
import com.qms.admin.repository.CategoryRepository;
import com.qms.admin.service.CategoryService;
import com.qms.common.model.Category;
import com.qms.common.repository.QuizRepository;

@Service
public class CategoryServiceimpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private QuizRepository quizRepository;

	@Override
	public CategoryResponse addCategory(final CategoryRequest categoryRequest) {
		Category category = categoryRepository.save(mapToModel(new Category(), categoryRequest));

		CategoryResponse response = new CategoryResponse();
		response.setData(response.new Data(category.getId(), category.getName())).setHttpStatus(HttpStatus.CREATED)
				.setMessage(AdminMessageConstant.CATGEORY_CREATED).setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public CategoryResponse updateCategory(final Long categoryId, final CategoryRequest categoryRequest) {

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new CategoryNotExistException(AdminMessageConstant.CATEGORY_NOT_EXIST));

		categoryRepository.save(mapToModel(category, categoryRequest));

		CategoryResponse response = new CategoryResponse();
		response.setData(response.new Data(category.getId(), category.getName())).setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.CATGEORY_UPDATED).setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public void deleteCategory(final Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new CategoryNotExistException(AdminMessageConstant.CATEGORY_NOT_EXIST));

		if (quizRepository.existsByCategoryId(category.getId())) {
			throw new CategoryConstraintViolationException(AdminMessageConstant.CATEGORY_QUIZ_ASSOCIATION_VIOLATION);
		}
		categoryRepository.delete(category);
	}

	@Override
	public CategoryResponse getCategory(final Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new CategoryNotExistException(AdminMessageConstant.CATEGORY_NOT_EXIST));

		CategoryResponse response = new CategoryResponse();
		response.setData(response.new Data(category.getId(), category.getName())).setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.CATGEORY_GOT).setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public ListCategoryResponse getCategoryList() {

		List<CategoryDTO> categories = categoryRepository.findAll().stream().map(this::mapToDTO)
				.collect(Collectors.toCollection(ArrayList::new));

		ListCategoryResponse response = new ListCategoryResponse();
		response.setData(response.new Data(categories)).setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.CATGEORY_GOT).setResponseTime(LocalDateTime.now());
		return response;
	}

	private Category mapToModel(final Category category, final CategoryRequest categoryDTO) {
		return category.setName(categoryDTO.getName());
	}

	private CategoryDTO mapToDTO(final Category category) {
		return new CategoryDTO().setId(category.getId()).setCategoryName(category.getName());
	}

}
