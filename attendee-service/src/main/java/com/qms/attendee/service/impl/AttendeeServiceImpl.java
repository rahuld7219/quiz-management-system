package com.qms.attendee.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qms.attendee.dto.QuestionAnswer;
import com.qms.attendee.dto.QuizQuestionDTO;
import com.qms.attendee.dto.QuizQuestionQuestion;
import com.qms.attendee.dto.QuizResult;
import com.qms.attendee.dto.ResultDetail;
import com.qms.attendee.dto.request.QuizSubmission;
import com.qms.attendee.dto.response.Dashboard;
import com.qms.attendee.dto.response.Leaderboard;
import com.qms.attendee.dto.response.RankDetail;
import com.qms.attendee.model.Question;
import com.qms.attendee.repository.QuizQuestionRepository;
import com.qms.attendee.repository.QuizRepository;
import com.qms.attendee.repository.ScoreRepository;
import com.qms.attendee.service.AttendeeService;
import com.qms.attendee.service.QuizService;
import com.qms.attendee.util.RedisCacheUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

		List<QuizQuestionQuestion> quizQuestionQuestions = quizQuestionRepository // TODO: handle what if quiz id not
																					// exist ?
				.getQuestionByQuizIdAndDeleted(Long.valueOf(quizId), "N"); // TODO: use enum

		return quizQuestionQuestions.stream()
				.map(quizQuestionQuestion -> mapToQuizQuestionDTO(quizQuestionQuestion.getQuestion()))
				.collect(Collectors.toList());

		// TODO: The query running internally by JPA is not optimal,
		// but above implementation is just to show a way to get only particular fields
		// from the result.
		// Can also implement by query or by first get
		// question ids for given quiz id from quizQuestion repo then get question
		// details from question repo
	}

	private QuizQuestionDTO mapToQuizQuestionDTO(Question question) {
		return new QuizQuestionDTO().setQuestionId(question.getId()).setQuestionDetail(question.getQuestionDetail())
				.setOptionA(question.getOptionA()).setOptionB(question.getOptionB()).setOptionC(question.getOptionC())
				.setOptionD(question.getOptionD());
	}

	@Override
	public Dashboard dashboard() {
		return new Dashboard().setTotalQuizCount(this.quiService.getQuizCount())
				.setAttendedQuizCount(this.countAttendedQuiz()).setQuizCountByCategory(this.countQuizByCategory())
				.setAttendedQuizCountByCategory(this.countAttendedQuizByCategory());
	}

	@Override
	public Leaderboard leaderboard(final String quizId) {
//		TODO: extract this in a method
//		String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
//				.getUsername();

		final List<RankDetail> rankDetails = scoreRepository.getTopScorers(Long.valueOf(quizId), "rd@gmail.com"); // TODO: how to compare equals ignore case in mysql for email id
		final Leaderboard leaderboard = new Leaderboard();
		leaderboard.setRankList(rankDetails);
		
//		for (RankDetail rankDetail : rankDetails) {
//			if (rankDetail.getEmail().equalsIgnoreCase("rd2@gmail.com")) { // TODO: pass userId extracted from spring security
//				leaderboard.setYourRank(rankDetail);
//				break;
//			}
//		}
		
		return leaderboard;

//		List<Map<String, Object>> topScorers = scoreRepository.getTopScorers(Long.valueOf(quizId));
//
//		return createLeaderboard(topScorers, "rd@gmail.com");
	}

//	private Leaderboard createLeaderboard(List<Map<String, Object>> topScorers, String email) {
//
//		final Leaderboard leaderboard = new Leaderboard();
//		final List<RankDetail> rankList = new ArrayList<>();
//
//		for (Map<String, Object> scorer : topScorers) {
//			RankDetail rankDetail = new RankDetail();
//			rankDetail.setRank(Long.valueOf(scorer.get("rank").toString())).setName((String) scorer.get("name"))
//					.setEmail((String) scorer.get("email"))
//					.setTotalScore(Long.valueOf(scorer.get("totalScore").toString()));
//
//			rankList.add(rankDetail);
//			if (rankDetail.getEmail().equalsIgnoreCase(email)) {
//				leaderboard.setYourRank(rankDetail);
//			}
//		}
//		leaderboard.setRankList(rankList);
//		return leaderboard;
//	}

	@Override
	public void submitQuiz(final QuizSubmission quizSubmission) {
//		TODO: extract this in a method
//		String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
//				.getUsername();

		redisCacheUtil.cacheSubmission("rd2@gmail.com_" + quizSubmission.getQuizId(), quizSubmission); // TODO: pass
																										// userId
																										// extracted
																										// from spring
																										// security
																										// context

	}

	@Override
	public QuizResult showResult(final String quizId) {

		QuizSubmission quizSubmission = redisCacheUtil.getCachedSubmission("rd2@gmail.com_" + quizId); // TODO: handle
																										// exception if
																										// key not found

		log.info("====quiz submisiion=== {} ", quizSubmission);

		List<QuizQuestionQuestion> quizQuestionQuestions = quizQuestionRepository // TODO: handle what if quiz id not
																					// exist ?
				.getQuestionByQuizIdAndDeleted(Long.valueOf(quizId), "N"); // TODO: use enum

		final Map<Long, Question> questionsMap = createQuestionsMap(quizQuestionQuestions);

		return computeQuizResult(quizSubmission, questionsMap);
	}

	/**
	 * 
	 * @param quizSubmission
	 * @param questionsMap
	 * @return
	 */
	private QuizResult computeQuizResult(QuizSubmission quizSubmission, final Map<Long, Question> questionsMap) { // TODO:
																													// think
																													// of
																													// optimizing
																													// it
																													// more

		Long correctAnswersCount = 0L;
		Long wrongAnswersCount = 0L;
		Long totalScore = 0L;
		List<ResultDetail> details = new ArrayList<>();

		for (QuestionAnswer questionAnswer : quizSubmission.getAnswerList()) {
			Question question = questionsMap.get(questionAnswer.getQuestionId());
			if (questionAnswer.getSelectedOption().equalsIgnoreCase(question.getRightOption())) {
				correctAnswersCount++;
				totalScore += question.getMarks();
			} else {
				wrongAnswersCount++; // TODO: can implement negative marking feature here
			}
			details.add(new ResultDetail().setQuestionDetail(question.getQuestionDetail())
					.setSubmittedAnswer(questionAnswer.getSelectedOption().toUpperCase()) // TODO: also show option's
																							// value
					.setCorrectAnswer(question.getRightOption())); // TODO: also show option's value
		}

		return new QuizResult().setCorrectAnswersCount(correctAnswersCount).setWrongAnswersCount(wrongAnswersCount)
				.setTotalScore(totalScore).setDetails(details);
	}

	/**
	 * Create a map with (k, v) as -> (questionId, question)
	 * 
	 * @param quizQuestionQuestions
	 * @return
	 */
	private Map<Long, Question> createQuestionsMap(List<QuizQuestionQuestion> quizQuestionQuestions) { // TODO: make it
																										// generic to
																										// create map
																										// with id, use
																										// generic
																										// concepts
		return quizQuestionQuestions.stream().collect(Collectors.toMap(
				quizQuestionQuestion -> quizQuestionQuestion.getQuestion().getId(), QuizQuestionQuestion::getQuestion));

//		Map<Long, Question> questionsMap = new HashMap<>();
//		
//		for (QuizQuestionQuestion quizQuestionQuestion : quizQuestionQuestions) {
//			questionsMap.put(quizQuestionQuestion.getQuestion().getId(), quizQuestionQuestion.getQuestion());
//		}
//		return questionsMap;
	}
}
