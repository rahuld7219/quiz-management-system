package com.qms.admin.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qms.admin.constant.RoleName;
import com.qms.admin.dto.Dashboard;
import com.qms.admin.dto.Leaderboard;
import com.qms.admin.dto.request.LinkQuizQuestion;
import com.qms.admin.model.Question;
import com.qms.admin.model.Quiz;
import com.qms.admin.model.QuizQuestion;
import com.qms.admin.model.Role;
import com.qms.admin.repository.QuestionRepository;
import com.qms.admin.repository.QuizQuestionRepository;
import com.qms.admin.repository.QuizRepository;
import com.qms.admin.repository.RoleRepository;
import com.qms.admin.repository.ScoreRepository;
import com.qms.admin.repository.UserRepository;
import com.qms.admin.service.AdminService;
import com.qms.admin.service.QuizService;

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
	public void linkQuestionToQuiz(final LinkQuizQuestion linkQuizQuestionDTO) {
		// TODO: find by Id and and deleted is "N"
		Question question = questionRepository
				.findByIdAndDeleted(Long.valueOf(linkQuizQuestionDTO.getQuestionId()), "N") // TODO: use
				// questionService
				.orElseThrow(() -> new RuntimeException("Question not exist.")); // TODO: create custom exception

		// TODO: find by Id and and deleted is "N"
		Quiz quiz = quizRepository.findByIdAndDeleted(Long.valueOf(Long.valueOf(linkQuizQuestionDTO.getQuizId())), "N") // TODO:
				// use
				// quizService
				.orElseThrow(() -> new RuntimeException("Quiz not exist.")); // TODO: create custom exception

		if (quizQuestionRepository.existsByQuestionId(quiz.getId())) { // also consider if deleted is 'N' ==>
																		// quizQuestionRepository.existsByQuestionIdAndDeleted(quizId,
																		// "N")
			throw new RuntimeException("Question is already linked to the quiz.");

			// TODO: OR can return simply??
		}

		// TODO: if question exist and deleted is "Y" in QuizQuestion then just set the
		// deleted to "N" and don't add the question again i.e., not execute below lines

		QuizQuestion quizQuestion = new QuizQuestion();
		quizQuestion.setQuestion(question);
		quizQuestion.setQuiz(quiz);

		quizQuestionRepository.save(quizQuestion); // TODO: save list of questions and return count and ids of saved question
	}

	@Override
	public Long countAttendess() {
		return userRepository.countByRolesRoleName(RoleName.ATTENDEE);
	}

	@Override
	public Long countAttendeesAttemptedQuiz() {
//		return userRepository.countDistinctByScoresUserRolesRoleName(RoleName.ATTENDEE);
		Optional<Role> role = roleRepository.findByRoleName(RoleName.ATTENDEE);
		if (!role.isPresent()) {
			return 0L;
		}
		return userRepository.countAttendeeAttemptedQuiz(role.get().getId());
	}

	@Override
	public List<Map<String, Object>> countTopFiveQuizWithAttendee() {
		return scoreRepository.getAttendeeCountGroupByQuiz();
	}

	@Override
	public Dashboard dashboard() {
		return new Dashboard().setNumberOfQuizzes(this.quizService.getQuizCount())
				.setTotalAttendees(this.countAttendess())
				.setTopFiveAttendedQuizzes(this.countTopFiveQuizWithAttendee());
	}

//	@Override
//	public List<Map<String, Object>> leaderboard() {
//		return scoreRepository.getRecordFilteredByCategoryThenQuizThenAttendeeScore();
//		// TODO: add pagination and also limit by category and limit by quiz per category and also limit by users per category per quiz
//	}

	@Override
	public Leaderboard leaderboard(final String quizId) {
		return new Leaderboard().setRankList(scoreRepository.getTopScorers(Long.valueOf(quizId)));
	}

}
