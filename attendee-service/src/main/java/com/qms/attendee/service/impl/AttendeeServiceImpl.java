package com.qms.attendee.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

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
import com.qms.attendee.model.Quiz;
import com.qms.attendee.model.Score;
import com.qms.attendee.model.User;
import com.qms.attendee.repository.QuizQuestionRepository;
import com.qms.attendee.repository.QuizRepository;
import com.qms.attendee.repository.ScoreRepository;
import com.qms.attendee.repository.UserRepository;
import com.qms.attendee.service.AttendeeService;
import com.qms.attendee.service.QuizService;
import com.qms.attendee.util.PDFGenerator;
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

	@Autowired
	private UserRepository userRepository;

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

		final List<RankDetail> rankDetails = scoreRepository.getTopScorers(Long.valueOf(quizId), "rd2@gmail.com"); // TODO:
																													// how
																													// to
																													// compare
																													// equals
																													// ignore
																													// case
																													// in
																													// mysql
																													// for
																													// email
																													// id
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

	// TODO: Optimize this and its dependent methods
	@Override
	public QuizResult showResult(final String quizId) {
//		TODO: extract this in a method
//		String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
//				.getUsername();
		
		User user = userRepository.findByEmailId("rd2@gmail.com") // TODO: pass userId extracted from spring security context
				.orElseThrow(() -> new RuntimeException("User not exist.")); // TODO: pass from security context and use
																				// custom exception

		Quiz quiz = quizRepository.findById(Long.valueOf(quizId))
				.orElseThrow(() -> new RuntimeException("Quiz not exist.")); // TODO: use custom exception
		
		QuizResult quizResult = getQuizResult("rd2@gmail.com", quizId);  // TODO: pass userId extracted from spring security context

		// TODO: how to optimize this saving by preventing fetching of user and quiz? can change mapping by using ids instead of classes or can we write insert query in score repo directly.
		scoreRepository.save(new Score().setScoreValue(quizResult.getTotalScore()).setQuiz(quiz).setUser(user)); // TODO: what if show result method called multiple times, it will add duplicate entries? handle this case...can we set to run this line only once for each user each quiz id and after only once sumitQuiz() has been called again for that user and quiz id?? Or should we save only top score OR should we don't save if score is same for that quiz

		return quizResult;
	}

	private QuizResult getQuizResult(String email, String quizId) {
		
				QuizSubmission quizSubmission = redisCacheUtil.getCachedSubmission(email + "_" + quizId); // TODO: handle
																												// exception if
																												// key not found

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
					.setOptionA(question.getOptionA())
					.setOptionB(question.getOptionB())
					.setOptionC(question.getOptionC())
					.setOptionD(question.getOptionD())
					.setSubmittedAnswer(questionAnswer.getSelectedOption().toUpperCase()) // TODO: also show option's
																							// value
					.setCorrectAnswer(question.getRightOption())); // TODO: also show option's value
		}

		return new QuizResult().setExamDate(LocalDate.now()).setCorrectAnswersCount(correctAnswersCount).setWrongAnswersCount(wrongAnswersCount)
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

	// TODO: Optimize this and its dependent methods
	@Override
	public Object exportPDF(final String quizId, final HttpServletResponse response) {
//		TODO: extract this in a method
//		String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
//				.getUsername();
		String email = "rd2@gmail.com"; // TODO: pass userId extracted from spring security context

		User user = userRepository.findByEmailId(email) 
				.orElseThrow(() -> new RuntimeException("User not exist.")); // TODO: custom exception

		Quiz quiz = quizRepository.findById(Long.valueOf(quizId))
				.orElseThrow(() -> new RuntimeException("Quiz not exist.")); // TODO: use custom exception
		
		QuizResult quizResult = getQuizResult(email, quizId); // TODO: pass userId extracted from spring security context
		
		new PDFGenerator(quizResult, quiz.getTitle(), user.getFirstName() + " " + user.getLastName(), email, response).generatePdfReport(); 
		
		return null;
	}
}
