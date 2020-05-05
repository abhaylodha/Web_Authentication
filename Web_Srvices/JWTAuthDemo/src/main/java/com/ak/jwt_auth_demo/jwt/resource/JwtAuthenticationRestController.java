package com.ak.jwt_auth_demo.jwt.resource;

import java.util.Arrays;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ak.excel_utils.ExcelUtils;
import com.ak.jwt_auth_demo.jwt.JwtTokenUtil;
import com.ak.jwt_auth_demo.jwt.JwtUserDetails;

@RestController
@CrossOrigin
public class JwtAuthenticationRestController {

	@Value("${jwt.http.request.header}")
	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	private static ExcelUtils exl = new ExcelUtils("Users.xls");

	@RequestMapping(value = "${jwt.get.token.uri}", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest)
			throws AuthenticationException {

		System.out.println("User name : " + authenticationRequest.getUsername());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final String token = jwtTokenUtil.generateToken(userDetails);

		Long expirationDuration = jwtTokenUtil.getExpirationInSeconds() * 1000;

		return ResponseEntity.ok(new JwtTokenResponse(authenticationRequest.getUsername(),
				authenticationRequest.getUsername(), token, expirationDuration));
	}

	@RequestMapping(value = "${jwt.new.user.register.uri}", method = RequestMethod.POST)
	public ResponseEntity<?> registerANewUserAndReturnToken(HttpServletRequest request,
			@RequestBody JwtTokenRequest authenticationRequest) throws AuthenticationException {

		String authToken = request.getHeader(tokenHeader);
		final String token = authToken.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUserDetails user = (JwtUserDetails) jwtInMemoryUserDetailsService.loadUserByUsername(username);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		if (jwtTokenUtil.canTokenBeRefreshed(token)) {
			System.out.println("Adding new user : " + authenticationRequest.getUsername());
			exl.insertRecord("Users",
					Arrays.asList(authenticationRequest.getUsername(), authenticationRequest.getUsername(), "Panelist",
							encoder.encode(authenticationRequest.getPassword())));

			Long expirationDuration = jwtTokenUtil.getExpirationInSeconds() * 1000;

			return ResponseEntity.ok(new JwtTokenResponse(authenticationRequest.getUsername(),
					authenticationRequest.getUsername(), token, expirationDuration));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@RequestMapping(value = "${jwt.refresh.token.uri}", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String authToken = request.getHeader(tokenHeader);
		final String token = authToken.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUserDetails user = (JwtUserDetails) jwtInMemoryUserDetailsService.loadUserByUsername(username);

		if (jwtTokenUtil.canTokenBeRefreshed(token)) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			Long expirationDuration = jwtTokenUtil.getExpirationInSeconds() * 1000;

			return ResponseEntity.ok(new JwtTokenResponse(username, username, refreshedToken, expirationDuration));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new AuthenticationException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("INVALID_CREDENTIALS", e);
		}
	}
}
