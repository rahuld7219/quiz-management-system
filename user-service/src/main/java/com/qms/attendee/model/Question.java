package com.qms.attendee.model;

import java.time.OffsetDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Entity
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "question")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "question", nullable = false, unique = true)
	private String questionDetail;

	@Column(name = "option_a", nullable = false)
	private String optionA;

	@Column(name = "option_b", nullable = false)
	private String optionB;

	@Column(name = "option_c", nullable = false)
	private String optionC;

	@Column(name = "option_d", nullable = false)
	private String optionD;

	@Column(name = "right_option", nullable = false)
	private String rightOption;

	@Column(name = "marks", nullable = false)
	private int marks;

	@Column(name = "deleted", columnDefinition = "varchar(1) default 'N'")
	private String deleted = "N"; // TODO: use ENUM, why default not inserting??

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "question") // TODO: what could be the cascade?
	private Set<QuizQuestion> quizQuestions;

	@CreatedDate
	@Column(name = "created_on", nullable = false, updatable = false)
	private OffsetDateTime createdOn;

	@LastModifiedDate
	@Column(name = "updated_on", nullable = false)
	private OffsetDateTime updatedOn;
}
