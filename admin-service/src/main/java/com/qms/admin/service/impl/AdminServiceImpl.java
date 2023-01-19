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
import com.qms.admin.dto.Dashboard;
import com.qms.admin.dto.Leaderboard;
import com.qms.admin.dto.request.LinkQuizQuestionRequest;
import com.qms.admin.dto.response.CountAttendeesAttemptedQuizResponse;
import com.qms.admin.dto.response.CountAttendeesResponse;
import com.qms.admin.dto.response.CountTopFiveQuizWithAttendeeResponse;
import com.qms.admin.dto.response.DashboardResponse;
import com.qms.admin.dto.response.LeaderboardResponse;
import com.qms.admin.dto.response.LinkQuizQuestionResponse;
import com.qms.admin.repository.QuestionRepository;
import com.qms.admin.service.AdminService;
import com.qms.admin.service.QuizService;
import com.qms.common.constant.CommonMessageConstant;
import com.qms.common.constant.Deleted;
import com.qms.common.constant.RoleName;
import com.qms.common.exception.custom.QuizNotExistException;
import com.qms.common.model.Question;
import com.qms.common.model.Quiz;
import com.qms.common.model.QuizQuestion;
import com.qms.common.model.Role;
import com.qms.common.repository.QuizQuestionRepository;
import com.qms.common.repository.QuizRepository;
import com.qms.common.repository.RoleRepository;
import com.qms.common.repository.ScoreRepository;
import com.qms.common.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuizQuestionRepository quizQuestionRepository;

	@Autowired
	private ScoreRepository scoreRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private QuizService quizService;

	@Override
//	@Transactional
	public LinkQuizQuestionResponse linkQuestionToQuiz(final LinkQuizQuestionRequest linkQuizQuestionRequest) {

		// TODO: use question service
		Optional<List<Question>> existingQuestions = questionRepository
				.findAllByIdInAndDeleted(linkQuizQuestionRequest.getQuestionsIds(), Deleted.N);

		if (existingQuestions.isPresent() && !existingQuestions.get().isEmpty()) {

			return linkQuestionsAndCreateResponse(existingQuestions.get(), linkQuizQuestionRequest);
		}

		LinkQuizQuestionResponse response = new LinkQuizQuestionResponse();
		response.setData(
				response.new Data(new ArrayList<>(), new ArrayList<>(), linkQuizQuestionRequest.getQuestionsIds()))
				.setHttpStatus(HttpStatus.OK).setMessage(AdminMessageConstant.QUIZ_QUESTIONS_LINKED)
				.setResponseTime(LocalDateTime.now());
		return response;

	}

	// TODO: optimize it
	private LinkQuizQuestionResponse linkQuestionsAndCreateResponse(List<Question> existingQuestions,
			final LinkQuizQuestionRequest linkQuizQuestionRequest) {

		Quiz quiz = quizRepository.findByIdAndDeleted(linkQuizQuestionRequest.getQuizId(), Deleted.N)
				.orElseThrow(() -> new QuizNotExistException(CommonMessageConstant.QUIZ_NOT_EXIST));

		List<Long> existingQuestionIds = existingQuestions.stream().map(Question::getId).collect(Collectors.toList());

		List<Long> notExistingQuestionIds = linkQuizQuestionRequest.getQuestionsIds().stream()
				.filter(qId -> !existingQuestionIds.contains(qId)).collect(Collectors.toList());

		List<Question> questionsToLink = existingQuestions;

		// TODO: fetch only Questions
		Optional<List<QuizQuestion>> alreadyLinkedQuizQuestion = quizQuestionRepository
				.findAllByQuizIdAndDeletedAndQuestionIdIn(quiz.getId(), Deleted.N, existingQuestionIds);

		List<Long> alreadyLinkedQuestionIds = new ArrayList<>();
		if (alreadyLinkedQuizQuestion.isPresent() && !alreadyLinkedQuizQuestion.get().isEmpty()) {
			List<Question> alreadyLinkedQuestions = alreadyLinkedQuizQuestion.get().stream()
					.map(QuizQuestion::getQuestion).collect(Collectors.toList());
			alreadyLinkedQuestionIds = alreadyLinkedQuestions.stream().map(Question::getId)
					.collect(Collectors.toList());
			questionsToLink.removeAll(alreadyLinkedQuestions);
		}

		List<QuizQuestion> quizQuestionToAdd = new ArrayList<>();

		Optional<List<QuizQuestion>> alreadyLinkedQuestionButSoftDeleted = quizQuestionRepository
				.findAllByQuizIdAndDeletedAndQuestionIdIn(quiz.getId(), Deleted.Y, existingQuestionIds);

		if (alreadyLinkedQuestionButSoftDeleted.isPresent() && !alreadyLinkedQuestionButSoftDeleted.get().isEmpty()) {
			alreadyLinkedQuestionButSoftDeleted.get().forEach(quizQuestion -> quizQuestion.setDeleted(Deleted.N));
			questionsToLink.removeAll(alreadyLinkedQuestionButSoftDeleted.get().stream().map(QuizQuestion::getQuestion)
					.collect(Collectors.toList()));
			quizQuestionToAdd.addAll(alreadyLinkedQuestionButSoftDeleted.get());
		}

		for (Question question : questionsToLink) {

			QuizQuestion quizQuestion = new QuizQuestion();
			quizQuestion.setQuestion(question);
			quizQuestion.setQuiz(quiz);

			quizQuestionToAdd.add(quizQuestion);
		}

		List<Long> linkedQuestionIds = new ArrayList<>();
		if (!quizQuestionToAdd.isEmpty()) {
			linkedQuestionIds = quizQuestionRepository.saveAll(quizQuestionToAdd).stream()
					.map(quizQuestion -> quizQuestion.getQuestion().getId()).collect(Collectors.toList());
		}

		LinkQuizQuestionResponse response = new LinkQuizQuestionResponse();
		response.setData(response.new Data(linkedQuestionIds, alreadyLinkedQuestionIds, notExistingQuestionIds))
				.setHttpStatus(HttpStatus.CREATED).setMessage(AdminMessageConstant.QUIZ_QUESTIONS_LINKED)
				.setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public CountAttendeesResponse countAttendees() {
		CountAttendeesResponse response = new CountAttendeesResponse();
		response.setData(response.new Data(userRepository.countByRolesRoleName(RoleName.ATTENDEE)))
				.setHttpStatus(HttpStatus.OK).setMessage(AdminMessageConstant.ATTENDEES_COUNTED)
				.setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public CountAttendeesAttemptedQuizResponse countAttendeesAttemptedQuiz() {
		Optional<Role> role = roleRepository.findByRoleName(RoleName.ATTENDEE);
		Long theCount = 0L;
		if (role.isPresent()) {
			theCount = userRepository.countAttendeeAttemptedQuiz(role.get().getId());
		}

		CountAttendeesAttemptedQuizResponse response = new CountAttendeesAttemptedQuizResponse();
		response.setData(response.new Data(theCount)).setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.ATTENDEES_ATTEMPTED_QUIZ_COUNTED).setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public CountTopFiveQuizWithAttendeeResponse countTopFiveQuizWithAttendee() {
		CountTopFiveQuizWithAttendeeResponse response = new CountTopFiveQuizWithAttendeeResponse();
		response.setData(response.new Data(scoreRepository.getAttendeeCountGroupByQuiz())).setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.TOP_5_ATTENDEES_ATTEMPTED_QUIZ_COUNTED)
				.setResponseTime(LocalDateTime.now());
		return response;
	}

	@Override
	public DashboardResponse dashboard() {

		DashboardResponse response = new DashboardResponse();
		response.setData(response.new Data(this.createDashboard())).setHttpStatus(HttpStatus.OK)
				.setMessage(AdminMessageConstant.DASHBOARD_SUCCESS).setResponseTime(LocalDateTime.now());
		return response;
	}

	private Dashboard createDashboard() {
		return new Dashboard().setNumberOfQuizzes(this.quizService.getQuizCount().getData().getQuizCount())
				.setTotalAttendees(this.countAttendees().getData().getAttendeesCount()).setTopFiveAttendedQuizzes(
						this.countTopFiveQuizWithAttendee().getData().getTopFiveQuizWithAttendeeCount());
	}

	@Override
	public LeaderboardResponse leaderboard(final Long quizId) {
		LeaderboardResponse response = new LeaderboardResponse();
		response.setData(response.new Data(new Leaderboard().setRankList(scoreRepository.getTopScorers(quizId))))
				.setHttpStatus(HttpStatus.OK).setMessage(AdminMessageConstant.LEADERBOARD_SUCCESS)
				.setResponseTime(LocalDateTime.now());
		return response;

	}

	/* Dashboard Old */
//	@Override
//	public List<Map<String, Object>> leaderboard() {
//		return scoreRepository.getRecordFilteredByCategoryThenQuizThenAttendeeScore();
//		// TODO: add pagination and also limit by category and limit by quiz per category and also limit by users per category per quiz
//	}
}
