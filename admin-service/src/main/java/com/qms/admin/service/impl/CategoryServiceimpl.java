package com.qms.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qms.admin.dto.CategoryDTO;
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
	public Long addCategory(final CategoryDTO categoryDTO) {
		return categoryRepository.save(mapToModel(new Category(), categoryDTO)).getId();
	}

	@Override
	public void updateCategory(final String categoryId, final CategoryDTO categoryDTO) {
		Category category = categoryRepository.findById(Long.valueOf(categoryId))
				.orElseThrow(() -> new RuntimeException("Category not exist.")); // TODO: make custom exception and add
																					// message constant
		categoryRepository.save(mapToModel(category, categoryDTO));
	}

	@Override
	public void deleteCategory(final String categoryId) {
		Category category = categoryRepository.findById(Long.valueOf(categoryId))
				.orElseThrow(() -> new RuntimeException("Category not exist.")); // TODO: make custom exception and add

		Optional<Quiz> quizOpt = quizRepository.findByCategoryId(category.getId());
		if (quizOpt.isPresent()) {
			throw new RuntimeException("Category cannot be deleted, it has associated quiz."); // TODO: make custom
																								// exception
		}
		categoryRepository.delete(category);
	}

	@Override
	public CategoryDTO getCategory(final String categoryId) {
		Category category = categoryRepository.findById(Long.valueOf(categoryId))
				.orElseThrow(() -> new RuntimeException("Category not exist.")); // TODO: make custom exception and add
																					// message
		return mapToDTO(category);
	}

	@Override
	public List<CategoryDTO> listCategories() {

		return categoryRepository.findAll().stream().map(this::mapToDTO)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	private Category mapToModel(final Category category, final CategoryDTO categoryDTO) {
		return category.setName(categoryDTO.getName());
	}

	private CategoryDTO mapToDTO(final Category category) {
		return new CategoryDTO().setName(category.getName());
	}

}
