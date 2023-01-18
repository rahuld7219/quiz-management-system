package com.qms.auth.constant;

public class AuthMessageConstant {

	private AuthMessageConstant() {
	}

	public static final String PASSWORD_CHANGE = "Password change successful.";
	public static final String USER_CREATED = "User created successfully.";
	public static final String LOGIN_SUCCESS = "Login successful.";
	public static final String REFRESH_SUCCESS = "Token renew Successful.";
	public static final String ROLES = "roles";

	public static final String USER_ALREADY_EXIST = "EmailId is already taken!.";
	public static final String USER_ROLE_NOT_FOUND = "User role not found.";
	public static final String INVALID_JWT = "JWT is not valid: ";
	public static final String REFRESH_TOKEN_NOT_MATCH = "Refresh token not exist.";
	public static final String NEW_PASSWORD_SIMILAR_TO_OLD = "New password cannot be similar to old password.";
	public static final String RE_ENTERED_PASSWORD_NOT_MATCH = "Re-entered new password does not match.";
	public static final String USER_NOT_FOUND = "User not exist.";
	public static final String WRONG_PASSWORD = "Wrong password provided.";

}
