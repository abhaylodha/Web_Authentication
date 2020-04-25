package com.ak.jwt_auth_demo.jwt.resource;

import java.io.Serializable;

public class JwtTokenRequest implements Serializable {

	private static final long serialVersionUID = -5616176897013108345L;

	private String username;
	private String password;

//	"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpbjI4bWludXRlcyIsImV4cCI6MTU4ODQ0NjkwNiwiaWF0IjoxNTg3ODQyMTA2fQ.bdBVQHJzOl_JRlykaJvzuLVChx3eN6X63JtlfAY1dIyh2Bsa1D7RaiUhc2GM7IPMlfP5tHvNqsZV1b6mJb0Xtg"
		
	public JwtTokenRequest() {
		super();
	}

	public JwtTokenRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
