package com.ak.jpa_example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ak.jpa_example.entities.User;
import com.ak.jpa_example.services.UserDAOService;

@Component
public class UserDAOServiceCommandLineRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(UserDAOServiceCommandLineRunner.class);

	@Autowired
	private UserDAOService userDAOService;

	@Override
	public void run(String... args) throws Exception {
		User user = new User("Jack", "Admin");
		long id = userDAOService.insert(user);

		user = new User("Jack", "Admin");
		id = userDAOService.insert(user);

		logger.info(String.format("User Created with id : %d", id) + user);

	}

}
