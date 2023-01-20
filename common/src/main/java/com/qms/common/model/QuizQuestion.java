package com.qms.common.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.qms.common.constant.Deleted;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "quiz_question")
public class QuizQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false) // TODO: what could be the cascade?
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;

	@ManyToOne(fetch = FetchType.LAZY, optional = false) // TODO: what could be the cascade?
	@JoinColumn(name = "question_id")
	private Question question;

	@Column(name = "deleted", columnDefinition = "varchar(1) default 'N'")
	@Enumerated(EnumType.STRING)
	private Deleted deleted = Deleted.N; // TODO: why default "N" not working

	@CreatedDate
	@Column(name = "created_on", nullable = false, updatable = false)
	private OffsetDateTime createdOn;

	@LastModifiedDate
	@Column(name = "updated_on", nullable = false)
	private OffsetDateTime updatedOn;

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		QuizQuestion other = (QuizQuestion) obj;
//		return Objects.equals(question, other.question) && Objects.equals(quiz, other.quiz);
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(question, quiz);
//	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) {
//			return true;
//		}
//
//		if (obj == null) {
//			return false;
//		}
//
//		if (getClass() != obj.getClass()) {
//			return false;
//		}
//
//		QuizQuestion other = (QuizQuestion) obj;
//
//		return Objects.equals(this.getQuestion().getQuestionDetail(), other.getQuestion().getQuestionDetail())
//				&& Objects.equals(this.getQuiz().getTitle(), other.getQuiz().getTitle());
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(this.getQuestion().getQuestionDetail(), this.getQuiz().getTitle());
//	}

}
