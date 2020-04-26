package com.ak.jpa_example;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ak.jpa_example.entities.User;
import com.ak.jpa_example.services.UserRepository;

@Component
public class UseRepositoryServiceCommandLineRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(UseRepositoryServiceCommandLineRunner.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		User user = new User("Jill", "Admin");
		userRepository.save(user);

		logger.info("User Created with using Spring Data (JpaRepository) " + user);

		Optional<User> foundUser = userRepository.findById(1L);

		logger.info("Found user : " + foundUser);
		logger.info("Found user ? : " + foundUser.isPresent());
		logger.info("Found user : " + foundUser.get());

	}

}
