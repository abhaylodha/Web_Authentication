package com.ak.jwt_auth_demo.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ak.excel_utils.ExcelUtils;

@Service
public class JwtInMemoryUserDetailsService implements UserDetailsService {

	private static ExcelUtils exl = new ExcelUtils("Users.xls");

	static List<JwtUserDetails> inMemoryUserList = new ArrayList<>();

	static {
		reloadUsers();
	}

	private static void reloadUsers() {
		inMemoryUserList.clear();
		List<Map<String, String>> data = exl.getAllRecords("Users");

		for (Map<String, String> d : data) {
			System.out.println("Loading " + d.get("user_name"));

			inMemoryUserList.add(new JwtUserDetails(d.get("id"), d.get("user_name"), d.get("password"), "ROLE_USER_2"));
		}
		inMemoryUserList.add(new JwtUserDetails("1", "in28minutes",
				"$2a$10$3zHzb.Npv1hfZbLEU5qsdOju/tk2je6W6PnNnY.c1ujWPcZh4PL6e", "ROLE_USER_2"));

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (!inMemoryUserList.stream().filter(user -> user.getUsername().equals(username)).findFirst().isPresent()) {
			reloadUsers();
		}
		Optional<JwtUserDetails> findFirst = inMemoryUserList.stream()
				.filter(user -> user.getUsername().equals(username)).findFirst();

		if (!findFirst.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}

		return findFirst.get();
	}

}
