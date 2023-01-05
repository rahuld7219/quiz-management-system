package com.qms.admin.model;

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

@Data
@NoArgsConstructor
@Entity
@Table(name = "quiz")
@EntityListeners(AuditingEntityListener.class)
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "title", nullable = false, unique = true)
	private String title;

	@Column(name = "deleted", columnDefinition = "varchar(1) default 'N'")
	private String deleted;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz") // TODO: what could be the cascade?
	private Set<QuizQuestion> quizQuestions;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz") // TODO: what could be the cascade?
	private Set<QuizCategory> quizCategories;

	@CreatedDate
	@Column(name = "created_on", nullable = false, updatable = false)
	private OffsetDateTime createdOn;

	@LastModifiedDate
	@Column(name = "updated_on", nullable = false)
	private OffsetDateTime updatedOn;
}
