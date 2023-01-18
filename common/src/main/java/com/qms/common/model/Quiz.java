package com.qms.common.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "quiz")
@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "title", nullable = false, unique = true) // TODO: unique is not working find out why, do we need to
																// change to 'create' mode or set explicitly in MySQL
																// itself, check in each model
	private String title;

	@Column(name = "deleted", columnDefinition = "varchar(1) default 'N'") // TODO: why this not inserting by default
																			// value N, check in each model
	private String deleted = "N";

	@ManyToOne(fetch = FetchType.LAZY) // TODO: what could be the cascade?
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz") // TODO: what could be the cascade?
	private Set<QuizQuestion> quizQuestions;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz")
	private List<Score> scores;

	@CreatedDate
	@Column(name = "created_on", nullable = false, updatable = false)
	private OffsetDateTime createdOn;

	@LastModifiedDate
	@Column(name = "updated_on", nullable = false)
	private OffsetDateTime updatedOn;
}
