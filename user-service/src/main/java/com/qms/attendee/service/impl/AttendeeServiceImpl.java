package com.qms.attendee.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qms.attendee.dto.Dashboard;
import com.qms.attendee.dto.QuestionAnswer;
import com.qms.attendee.dto.QuestionDTO;
import com.qms.attendee.dto.QuizQuestionDTO;
import com.qms.attendee.dto.QuizQuestionQuestion;
import com.qms.attendee.dto.QuizResult;
import com.qms.attendee.dto.QuizSubmission;
import com.qms.attendee.dto.ResultDetail;
import com.qms.attendee.model.Question;
import com.qms.attendee.repository.QuizQuestionRepository;
import com.qms.attendee.repository.QuizRepository;
import com.qms.attendee.repository.ScoreRepository;
import com.qms.attendee.service.AttendeeService;
import com.qms.attendee.service.QuizService;
import com.qms.attendee.util.RedisCacheUtil;

@Service
public class AttendeeServiceImpl implements AttendeeService {

	@Autowired
	private ScoreRepository scoreRepository;

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private QuizQuestionRepository quizQuestionRepository;
	
	@Autowired
	private QuizService quiService;
	
	@Autowired
	private RedisCacheUtil redisCacheUtil;

	@Override
	public Long countAttendedQuiz() {
//		TODO: extract this in a method
//		String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
//				.getUsername();
//		Long userId = userRepository.getIdByEmail(email);

		return scoreRepository.countDistinctQuizByUserId(2L); // TODO: pass userId extracted from spring security
																// context
	}

	@Override
	public List<Map<String, Object>> countQuizByCategory() {
		return quizRepository.countQuizByCategory();
	}

	@Override
	public List<Map<String, Object>> countAttendedQuizByCategory() {
		return quizRepository.countAttendedQuizByCategory();
	}

	@Override
	public List<QuizQuestionDTO> getQuizQuestions(final String quizId) {

		List<QuizQuestionQuestion> quizQuestionQuestions = quizQuestionRepository
				.getQuestionByQuizId(Long.valueOf(quizId));

		return quizQuestionQuestions.stream().map(quizQuestionQuestion -> mapToDTO(quizQuestionQuestion.getQuestion()))
				.collect(Collectors.toList());

		// TODO: The query running internally by JPA is not optimal,
		// but above implementation is just to show a way to get only particular fields from the result. 
		// Can also implement by query or by first get
		// question ids for given quiz id from quizQuestion repo then get question
		// details from question repo
	}

	private QuizQuestionDTO mapToDTO(Question question) {
		return new QuizQuestionDTO().setQuestionDetail(question.getQuestionDetail()).setOptionA(question.getOptionA())
				.setOptionB(question.getOptionB()).setOptionC(question.getOptionC()).setOptionD(question.getOptionD());
	}

	@Override
	public Dashboard dashboard() {
		return new Dashboard()
				.setTotalQuizCount(this.quiService.getQuizCount())
				.setAttendedQuizCount(this.countAttendedQuiz())
				.setQuizCountByCategory(this.countQuizByCategory())
				.setAttendedQuizCountByCategory(this.countAttendedQuizByCategory());
	}

	@Override
	public void submitQuiz(final QuizSubmission quizSubmission) {
//		TODO: extract this in a method
//		String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
//				.getUsername();
		
		redisCacheUtil.cacheSubmission("rd2@gmail.com_" + quizSubmission.getQuizId(), quizSubmission); // TODO: pass userId extracted from spring security context
		
	}

	@Override
	public QuizResult showResult(String quizId) {
		QuizSubmission quizSubmission = redisCacheUtil.getCachedSubmission("rd2@gmail.com_" + quizId); // TODO: handle exception if key not match
		System.out.println(quizSubmission);
		List<QuestionDTO> questions = quizQuestionRepository.getQuestionByQuizIdCopy(Long.valueOf(quizId));
		Long correctAnswersCount = 0L;
		Long wrongAnswersCount = 0L;
		Long totalScore = 0L;
		List<ResultDetail> details = new ArrayList<>();
		
		// TODO: optimize and use stream
		for (QuestionAnswer questionAnswer : quizSubmission.getAnswerList()) {
			for (QuestionDTO question : questions) {
				if (Objects.equals(questionAnswer.getQuestionId(), question.getQuestionId())) {
					if (questionAnswer.getSelectedOption().equalsIgnoreCase(question.getRightOption())) {
						correctAnswersCount++;
						totalScore += question.getMarks();
					} else {
						wrongAnswersCount++;
					}
					details.add(new ResultDetail()
					.setQuestionDetail(question.getQuestionDetail())
					.setSubmittedAnswer(questionAnswer.getSelectedOption())
					.setCorrectAnswer(question.getRightOption()));
				}
			}
		}
		
		return new QuizResult().setCorrectAnswersCount(correctAnswersCount)
				.setWrongAnswersCount(wrongAnswersCount)
				.setTotalScore(totalScore)
				.setDetails(details);
	}
}
