package com.ak.jwt_auth_demo.hello;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HelloController {

	@GetMapping(path = "/hello")
	public String Hello() {
		return ("Hello");
	}

	@GetMapping(path = "/hellobean/{msg}")
	public HelloBean Hello(@PathVariable String msg) {
		return new HelloBean(msg);
	}

}
