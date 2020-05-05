package com.ak.jwt_auth_demo.jwt.resource;

import java.io.Serializable;

public class JwtTokenResponse implements Serializable {

	private static final long serialVersionUID = 8317676219297719109L;

	private final String email;
	private final String localId;
	private final String idToken;
	private final Long expiresIn;

	public JwtTokenResponse(String email, String localId, String idToken, Long expiresIn) {
		super();
		this.email = email;
		this.localId = localId;
		this.idToken = idToken;
		this.expiresIn = expiresIn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEmail() {
		return email;
	}

	public String getLocalId() {
		return localId;
	}

	public String getIdToken() {
		return idToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}
}