package com.qms.admin.constant;

public class AdminMessageConstant {

	private AdminMessageConstant() {
	}

	public static final String QUESTION_ADDED = "Question added successfully to the quiz";
	public static final String QUIZ_QUESTIONS_LINKED = "Given existing non-linked questions linked to the quiz.";
	public static final String ATTENDEES_COUNTED = "Attendees count successful.";
	public static final String ATTENDEES_ATTEMPTED_QUIZ_COUNTED = "Attendees attempted quizzes count successful.";
	public static final String TOP_5_ATTENDEES_ATTEMPTED_QUIZ_COUNTED = "Attendees attempted top 5 quizzes count successful.";
	public static final String DASHBOARD_SUCCESS = "Dashboard response successful.";
	public static final String LEADERBOARD_SUCCESS = "Leaderboard response successful.";

	public static final String CATGEORY_CREATED = "Category create successful.";
	public static final String CATGEORY_UPDATED = "Category update successful.";
	public static final String CATGEORY_DELETED = "Category delete Successful.";
	public static final String CATGEORY_GOT = "Category fetch successful.";

	public static final String QUESTION_CREATED = "Question create successful.";
	public static final String QUESTION_UPDATED = "Question update successful.";
	public static final String QUESTION_DELETED = "Question delete Successful.";
	public static final String QUESTION_GOT = "Question fetch successful.";

	public static final String QUIZ_CREATED = "Quiz create successful.";
	public static final String QUIZ_UPDATED = "Quiz update successful.";
	public static final String QUIZ_DELETED = "Quiz delete Successful.";
	public static final String QUIZ_GOT = "Quiz fetch successful.";

	public static final String QUESTION_NOT_EXIST = "Question not exist.";
//	public static final String QUIZ_NOT_EXIST = "Quiz not exist.";
	public static final String CATEGORY_NOT_EXIST = "Category not exist.";
	public static final String CATEGORY_QUIZ_ASSOCIATION_VIOLATION = "Category cannot be deleted, it has associated quiz.";
	public static final String QUESTION_QUIZ_ASSOCIATION_VIOLATION = "Question cannot be deleted, it has associated quiz.";
	public static final String QUIZ_SCORE_ASSOCIATION_VIOLATION = "Quiz cannot be deleted, it has associated score.";
}
