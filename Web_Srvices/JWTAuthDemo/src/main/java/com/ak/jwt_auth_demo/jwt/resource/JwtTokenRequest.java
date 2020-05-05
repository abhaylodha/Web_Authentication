package com.ak.jwt_auth_demo.jwt.resource;

import java.io.Serializable;

public class JwtTokenRequest implements Serializable {

	private static final long serialVersionUID = -5616176897013108345L;

	private String email;
	private String password;
	private Boolean returnSecureToken;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getReturnSecureToken() {
		return returnSecureToken;
	}

	public void setReturnSecureToken(Boolean returnSecureToken) {
		this.returnSecureToken = returnSecureToken;
	}

	public JwtTokenRequest() {
		super();
	}

	public JwtTokenRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.email;
	}

	public void setUsername(String username) {
		this.email = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
