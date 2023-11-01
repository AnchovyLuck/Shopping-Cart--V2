package com.springweb.model;

import java.util.Set;

import jakarta.validation.constraints.Size;

public class UserRegistrationDto {
	@Size(min = 5, max = 30,
			message = "Your password '${validateValue}' must be between {min} and {max} characters long")
	private String password;
	
	@Size(min = 5, max = 15,
			message = "Your username '${validateValue}' must be between {min} and {max} characters long")
	private String username;
	
	@Size(min = 1, message = "Please provide your first name")
	private String firstName;
	
	@Size(min = 1, message = "Please provide your last name")
	private String lastName;
	
	@Size(min = 10, max = 25,
			message = "Your email '${validateValue}' must be between {min} and {max} characters long")
	private String email;
	
	@Size(min = 1, max = 10,
			message = "Your ROLE '${validateValue}' must be between {min} and {max} characters long")
	private Set<String> roles;

	public UserRegistrationDto() {
	}
	
	

	public UserRegistrationDto(
			@Size(min = 5, max = 30, message = "Your password '${validateValue}' must be between {min} and {max} characters long") String password,
			@Size(min = 5, max = 15, message = "Your username '${validateValue}' must be between {min} and {max} characters long") String username,
			@Size(min = 1, message = "Please provide your first name") String firstName,
			@Size(min = 1, message = "Please provide your last name") String lastName,
			@Size(min = 10, max = 25, message = "Your email '${validateValue}' must be between {min} and {max} characters long") String email,
			@Size(min = 1, max = 10, message = "Your ROLE '${validateValue}' must be between {min} and {max} characters long") Set<String> roles) {
		this.password = password;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return "UserRegistrationDto [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + "]";
	}
}
