package com.qms.admin.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.qms.admin.constant.AdminMessageConstant;
import com.qms.admin.dto.request.CategoryRequest;
import com.qms.admin.dto.response.CategoryResponse;
import com.qms.admin.exception.custom.CategoryConstraintViolationException;
import com.qms.admin.exception.custom.CategoryNotExistException;
import com.qms.admin.repository.CategoryRepository;
import com.qms.admin.service.CategoryService;
import com.qms.common.model.Category;
import com.qms.common.model.Quiz;
import com.qms.common.repository.QuizRepository;

@Service
public class CategoryServiceimpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private QuizRepository quizRepository;

	@Override
	public CategoryResponse addCategory(final CategoryRequest categoryDTO) {
		Category category = categoryRepository.save(mapToModel(new Category(), categoryDTO));

		CategoryResponse response = new CategoryResponse();
		response.setData(response.new Data(category.getId(), category.getName())).setHttpStatus(HttpStatus.CREATED)
				.setMessage(AdminMessageConstant.CATGEORY_ADDED).setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public CategoryResponse updateCategory(final String categoryId, final CategoryRequest categoryDTO) {

		Category category = categoryRepository.findById(Long.valueOf(categoryId))
				.orElseThrow(() -> new CategoryNotExistException(AdminMessageConstant.CATEGORY_NOT_EXIST));

		categoryRepository.save(mapToModel(category, categoryDTO));

		CategoryResponse response = new CategoryResponse();
		response.setData(response.new Data(category.getId(), category.getName())).setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.CATGEORY_UPDATED).setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public void deleteCategory(final String categoryId) {
		Category category = categoryRepository.findById(Long.valueOf(categoryId))
				.orElseThrow(() -> new CategoryNotExistException(AdminMessageConstant.CATEGORY_NOT_EXIST));

		// TODO: try and catch databse exception instead of checking here
		Optional<Quiz> quizOpt = quizRepository.findByCategoryId(category.getId());
		if (quizOpt.isPresent()) {
			throw new CategoryConstraintViolationException("Category cannot be deleted, it has associated quiz."); // TODO: make custom
																								// exception
		}
		categoryRepository.delete(category);
	}

	@Override
	public CategoryRequest getCategory(final String categoryId) {
		Category category = categoryRepository.findById(Long.valueOf(categoryId))
				.orElseThrow(() -> new RuntimeException("Category not exist.")); // TODO: make custom exception and add
																					// message
		return mapToDTO(category);
	}

	@Override
	public List<CategoryRequest> listCategories() {

		return categoryRepository.findAll().stream().map(this::mapToDTO)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	private Category mapToModel(final Category category, final CategoryRequest categoryDTO) {
		return category.setName(categoryDTO.getName());
	}

	private CategoryRequest mapToDTO(final Category category) {
		return new CategoryRequest().setName(category.getName());
	}

}
