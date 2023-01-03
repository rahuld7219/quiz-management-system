package com.qms.auth.model;

import java.time.OffsetDateTime;
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

	@Column(name = "email_id", nullable = false)
	private String emailId;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "mobile_number", nullable = false)
	private String mobileNumber;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;// = new HashSet<>(); // so that if there is no role then empty set will be returned (not null)

//	@CreatedDate
//	@Column(name = "created_on", nullable = false, updatable = false)
//	private OffsetDateTime createdOn;
//
//	@LastModifiedDate
//	@Column(name = "updated_on", nullable = false)
//	private OffsetDateTime updatedOn;

	public User(String firstName, String lastName, String emailId, String password, String mobileNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.password = password;
		this.mobileNumber = mobileNumber;
	}

}
