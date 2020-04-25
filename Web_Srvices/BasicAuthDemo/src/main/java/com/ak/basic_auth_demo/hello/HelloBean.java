package com.ak.basic_auth_demo.hello;

public class HelloBean {

	String message;

	public HelloBean() {
	}

	public HelloBean(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
