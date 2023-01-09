//package com.qms.auth.service.impl;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.qms.auth.model.User;
//
//public class UserDetailsImpl implements UserDetails {
//	private static final long serialVersionUID = 1L;
//
//	private Long id;
//
//	private String username;
//
//	@JsonIgnore
//	private String password;
//
//	private Collection<? extends GrantedAuthority> authorities;
//
//	public UserDetailsImpl(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
//		this.id = id;
//		this.username = email;
//		this.password = password;
//		this.authorities = authorities;
//	}
//
//	public static UserDetailsImpl build(User user) {
//		List<GrantedAuthority> authorities = user.getRoles().stream()
//				.map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList()); // TODO:
//																													// explain
//
//		return new UserDetailsImpl(user.getId(), user.getEmailId(), user.getPassword(), authorities);
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return authorities;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	@Override
//	public String getPassword() {
//		return password;
//	}
//
//	@Override
//	public String getUsername() {
//		return username;
//	}
//
//	/* TODO:explain below methods */
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(id);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		UserDetailsImpl other = (UserDetailsImpl) obj;
//		return Objects.equals(id, other.id);
//	}
//
//}
