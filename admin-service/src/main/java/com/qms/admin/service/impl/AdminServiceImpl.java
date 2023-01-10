package com.qms.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qms.admin.constant.RoleName;
import com.qms.admin.dto.LinkQuizQuestionDTO;
import com.qms.admin.model.Question;
import com.qms.admin.model.Quiz;
import com.qms.admin.model.QuizQuestion;
import com.qms.admin.repository.QuestionRepository;
import com.qms.admin.repository.QuizQuestionRepository;
import com.qms.admin.repository.QuizRepository;
import com.qms.admin.repository.UserRepository;
import com.qms.admin.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuizQuestionRepository quizQuestionRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void linkQuestionToQuiz(final LinkQuizQuestionDTO linkQuizQuestionDTO) {
		//TODO: find by Id and and deleted is "N"  
		Question question = questionRepository.findByIdAndDeleted(Long.valueOf(linkQuizQuestionDTO.getQuestionId()), "N") // TODO: use
																											// questionService
				.orElseThrow(() -> new RuntimeException("Question not exist.")); // TODO: create custom exception

		//TODO: find by Id and and deleted is "N"
		Quiz quiz = quizRepository.findByIdAndDeleted(Long.valueOf(Long.valueOf(linkQuizQuestionDTO.getQuizId())), "N") // TODO:
																											// use
																											// quizService
				.orElseThrow(() -> new RuntimeException("Quiz not exist.")); // TODO: create custom exception
		
		if (quizQuestionRepository.existsByQuestionId(quiz.getId())) { // also consider if deleted is 'N' ==> quizQuestionRepository.existsByQuestionIdAndDeleted(quizId, "N")
			throw new RuntimeException("Question is already linked to the quiz.");
			
			// TODO: OR can return simply??
		}
		
		//TODO: if question exist and deleted is "Y" in QuizQuestion then just set the deleted to "N" and don't add the question again i.e., not execute below lines  
		
		QuizQuestion quizQuestion = new QuizQuestion();
		quizQuestion.setQuestion(question);
		quizQuestion.setQuiz(quiz);
		
		quizQuestionRepository.save(quizQuestion);
	}

	@Override
	public Long countAttendess() {
		return userRepository.countByRoles_RoleName(RoleName.ADMIN);
	}

}
