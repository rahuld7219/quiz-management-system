package com.qms.attendee.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.qms.attendee.constant.AttendeeMessageConstant;
import com.qms.attendee.dto.Dashboard;
import com.qms.attendee.dto.Leaderboard;
import com.qms.attendee.dto.QuestionAnswer;
import com.qms.attendee.dto.QuizQuestionDTO;
import com.qms.attendee.dto.QuizResult;
import com.qms.attendee.dto.ResultDetail;
import com.qms.attendee.dto.request.QuizSubmission;
import com.qms.attendee.dto.response.CountAttendedQuizByCategoryResponse;
import com.qms.attendee.dto.response.CountAttendedQuizResponse;
import com.qms.attendee.dto.response.CountQuizByCategoryResponse;
import com.qms.attendee.dto.response.DashboardResponse;
import com.qms.attendee.dto.response.GetQuizQuestionsReponse;
import com.qms.attendee.dto.response.LeaderboardResponse;
import com.qms.attendee.dto.response.ShowResultResponse;
import com.qms.attendee.service.AttendeeService;
import com.qms.attendee.service.QuizService;
import com.qms.attendee.util.AttendeeRedisCacheUtil;
import com.qms.attendee.util.PDFGenerator;
import com.qms.common.constant.CommonMessageConstant;
import com.qms.common.constant.Deleted;
import com.qms.common.dto.QuizQuestionQuestion;
import com.qms.common.dto.RankDetail;
import com.qms.common.exception.custom.QuizNotExistException;
import com.qms.common.model.Question;
import com.qms.common.model.Quiz;
import com.qms.common.model.Score;
import com.qms.common.model.User;
import com.qms.common.repository.QuizQuestionRepository;
import com.qms.common.repository.QuizRepository;
import com.qms.common.repository.ScoreRepository;
import com.qms.common.repository.UserRepository;

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
	private AttendeeRedisCacheUtil redisCacheUtil;

	@Autowired
	private UserRepository userRepository;

	@Override
	public CountAttendedQuizResponse countAttendedQuiz() {

		final String email = extractUserFromSecurityContext();

		final Long userId = userRepository.findByEmailId(email).get().getId();

		CountAttendedQuizResponse response = new CountAttendedQuizResponse();
		response.setData(response.new Data(email, scoreRepository.countDistinctQuizByUserId(userId)))
				.setHttpStatus(HttpStatus.OK).setMessage(AttendeeMessageConstant.ATTENDED_QUIZ_COUNTED)
				.setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public CountQuizByCategoryResponse countQuizByCategory() {

		CountQuizByCategoryResponse response = new CountQuizByCategoryResponse();
		response.setData(response.new Data(quizRepository.countQuizByCategory())).setHttpStatus(HttpStatus.OK)
				.setMessage(AttendeeMessageConstant.QUIZ_BY_CATEGORY_COUNTED).setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public CountAttendedQuizByCategoryResponse countAttendedQuizByCategory() {
		final String email = extractUserFromSecurityContext();

		final Long userId = userRepository.findByEmailId(email).get().getId();

		CountAttendedQuizByCategoryResponse response = new CountAttendedQuizByCategoryResponse();
		response.setData(response.new Data(quizRepository.countAttendedQuizByCategory(userId)))
				.setHttpStatus(HttpStatus.OK).setMessage(AttendeeMessageConstant.ATTENDED_QUIZ_BY_CATEGORY_COUNTED)
				.setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public GetQuizQuestionsReponse getQuizQuestions(final Long quizId) {

		List<QuizQuestionQuestion> quizQuestionQuestions = quizQuestionRepository.getQuestionByQuizIdAndDeleted(quizId,
				Deleted.N);

		List<QuizQuestionDTO> quizQuestions = quizQuestionQuestions.stream()
				.map(quizQuestionQuestion -> mapToQuizQuestionDTO(quizQuestionQuestion.getQuestion()))
				.collect(Collectors.toList());

		GetQuizQuestionsReponse response = new GetQuizQuestionsReponse();
		response.setData(response.new Data(quizQuestions)).setHttpStatus(HttpStatus.OK)
				.setMessage(AttendeeMessageConstant.QUIZ_QUESTIONS_GOT).setResponseTime(LocalDateTime.now());
		return response;
	}

	private QuizQuestionDTO mapToQuizQuestionDTO(Question question) {
		return new QuizQuestionDTO().setQuestionId(question.getId()).setQuestionDetail(question.getQuestionDetail())
				.setOptionA(question.getOptionA()).setOptionB(question.getOptionB()).setOptionC(question.getOptionC())
				.setOptionD(question.getOptionD());
	}

	@Override
	public DashboardResponse dashboard() {
		Dashboard dashboard = new Dashboard().setTotalQuizCount(this.quiService.getQuizCount().getData().getQuizCount())
				.setAttendedQuizCount(this.countAttendedQuiz().getData().getAttendedQuizCount())
				.setQuizCountByCategory(this.countQuizByCategory().getData().getQuizCountByCategory())
				.setAttendedQuizCountByCategory(
						this.countAttendedQuizByCategory().getData().getAttendedQuizCountByCategory());

		DashboardResponse response = new DashboardResponse();
		response.setData(response.new Data(dashboard)).setHttpStatus(HttpStatus.OK)
				.setMessage(AttendeeMessageConstant.DASHBOARD_SUCCESS).setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public LeaderboardResponse leaderboard(final Long quizId) {

		final String email = this.extractUserFromSecurityContext();

		final List<RankDetail> rankDetails = scoreRepository.getTopScorers(quizId, email);

		final Leaderboard leaderboard = new Leaderboard();
		leaderboard.setYourRank(rankDetails.get(0));
		leaderboard.setRankList(rankDetails.subList(1, rankDetails.size()));

		LeaderboardResponse response = new LeaderboardResponse();
		response.setData(response.new Data(leaderboard)).setHttpStatus(HttpStatus.OK)
				.setMessage(AttendeeMessageConstant.LEADERBOARD_SUCCESS).setResponseTime(LocalDateTime.now());
		return response;

	}

	@Override
	public void submitQuiz(final QuizSubmission quizSubmission) {

		if (!quizRepository.existsById(quizSubmission.getQuizId())) {
			throw new QuizNotExistException(CommonMessageConstant.QUIZ_NOT_EXIST);
		}

		final String email = this.extractUserFromSecurityContext();

		redisCacheUtil.cacheSubmission(email + "_" + quizSubmission.getQuizId(), quizSubmission);

	}

	@Override
	public ShowResultResponse showResult(final Long quizId) {

		final String email = this.extractUserFromSecurityContext();

		User user = userRepository.findByEmailId(email)
				.orElseThrow(() -> new UsernameNotFoundException(CommonMessageConstant.USER_NOT_FOUND + email));

		Quiz quiz = quizRepository.findById(quizId)
				.orElseThrow(() -> new QuizNotExistException(CommonMessageConstant.QUIZ_NOT_EXIST));

		QuizResult quizResult = getQuizResult(email, quizId);

		scoreRepository.save(new Score().setScoreValue(quizResult.getTotalScore()).setQuiz(quiz).setUser(user));

		ShowResultResponse response = new ShowResultResponse();
		response.setData(response.new Data(quizResult)).setHttpStatus(HttpStatus.OK)
				.setMessage(AttendeeMessageConstant.SHOW_RESULT_SUCCESS).setResponseTime(LocalDateTime.now());
		return response;
	}

	private QuizResult getQuizResult(String email, Long quizId) {

		QuizSubmission quizSubmission = redisCacheUtil.getCachedSubmission(email + "_" + quizId);

		List<QuizQuestionQuestion> quizQuestionQuestions = quizQuestionRepository.getQuestionByQuizIdAndDeleted(quizId,
				Deleted.N);

		final Map<Long, Question> questionsMap = createQuestionsMap(quizQuestionQuestions);

		return computeQuizResult(quizSubmission, questionsMap);
	}

	private QuizResult computeQuizResult(QuizSubmission quizSubmission, final Map<Long, Question> questionsMap) {

		Long correctAnswersCount = 0L;
		Long wrongAnswersCount = 0L;
		Long totalScore = 0L;
		List<ResultDetail> details = new ArrayList<>();

		for (QuestionAnswer questionAnswer : quizSubmission.getAnswerList()) {
			Question question = questionsMap.get(questionAnswer.getQuestionId());

			// if question id is not in the quiz, skip it
			if (Objects.isNull(question)) {
				continue;
			}

			if (questionAnswer.getSelectedOption().equalsIgnoreCase(question.getRightOption())) {
				correctAnswersCount++;
				totalScore += question.getMarks();
			} else {
				wrongAnswersCount++;
			}
			details.add(new ResultDetail().setQuestionDetail(question.getQuestionDetail())
					.setOptionA(question.getOptionA()).setOptionB(question.getOptionB())
					.setOptionC(question.getOptionC()).setOptionD(question.getOptionD())
					.setSubmittedAnswer(questionAnswer.getSelectedOption().toUpperCase())
					.setCorrectAnswer(question.getRightOption()));
		}

		return new QuizResult().setExamDate(LocalDate.now()).setCorrectAnswersCount(correctAnswersCount)
				.setWrongAnswersCount(wrongAnswersCount).setTotalScore(totalScore).setDetails(details);
	}

	/**
	 * Create a map with (k, v) as -> (questionId, question)
	 * 
	 * @param quizQuestionQuestions
	 * @return
	 */
	private Map<Long, Question> createQuestionsMap(List<QuizQuestionQuestion> quizQuestionQuestions) {
		return quizQuestionQuestions.stream().collect(Collectors.toMap(
				quizQuestionQuestion -> quizQuestionQuestion.getQuestion().getId(), QuizQuestionQuestion::getQuestion));
	}

	@Override
	public Object exportPDF(final Long quizId, final HttpServletResponse response) {

		final String email = this.extractUserFromSecurityContext();

		User user = userRepository.findByEmailId(email)
				.orElseThrow(() -> new UsernameNotFoundException(CommonMessageConstant.USER_NOT_FOUND + email));

		Quiz quiz = quizRepository.findById(quizId)
				.orElseThrow(() -> new QuizNotExistException(CommonMessageConstant.QUIZ_NOT_EXIST));

		QuizResult quizResult = getQuizResult(email, quizId);

		new PDFGenerator(quizResult, quiz.getTitle(), user.getFirstName() + " " + user.getLastName(), email, response)
				.generatePdfReport();

		return null;
	}

	private String extractUserFromSecurityContext() {
		return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}
}
