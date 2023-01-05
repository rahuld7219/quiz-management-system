package com.qms.admin.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "quiz_category")
public class QuizCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false) // TODO: what could be the cascade?
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;

	@ManyToOne(fetch = FetchType.LAZY, optional = false) // TODO: what could be the cascade?
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(name = "deleted", columnDefinition = "varchar(1) default 'N'")
	private String deleted; // TODO: use enum

	@CreatedDate
	@Column(name = "created_on", nullable = false, updatable = false)
	private OffsetDateTime createdOn;

	@LastModifiedDate
	@Column(name = "updated_on", nullable = false)
	private OffsetDateTime updatedOn;
}