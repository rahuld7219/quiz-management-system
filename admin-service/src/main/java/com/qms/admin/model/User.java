package com.qms.admin.model;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "email_id", nullable = false, unique = true) // TODO: throw proper standard formatted exception on
																// any constraint validation for any table
	private String emailId;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "mobile_number", nullable = false, unique = true)
	private String mobileNumber;

	@ManyToMany(fetch = FetchType.LAZY) // TODO: what could be the cascading rule? check in every entity class
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<Score> scores;

	@CreatedDate
	@Column(name = "created_on", nullable = false, updatable = false)
	private OffsetDateTime createdOn;

	@LastModifiedDate
	@Column(name = "updated_on", nullable = false)
	private OffsetDateTime updatedOn;
}
