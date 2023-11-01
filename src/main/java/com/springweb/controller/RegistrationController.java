package com.springweb.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springweb.model.Role;
import com.springweb.model.UserEntity;
import com.springweb.model.UserRegistrationDto;
import com.springweb.repository.RoleRepository;
import com.springweb.service.UserEntityServiceImpl;

import jakarta.validation.Valid;

@Controller
public class RegistrationController {
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserEntityServiceImpl userEntityServiceImpl;

	@GetMapping("/registration")
	public String registration(Model model) {
		UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
		model.addAttribute("userView", userRegistrationDto);

		return "/registration";
	}

	@PostMapping("/registration")
	public ModelAndView createNewUser(@ModelAttribute("userView") @Valid UserRegistrationDto userRegistration,
			BindingResult bindingResult) {
		if (userEntityServiceImpl.findByEmail(userRegistration.getEmail()).isPresent()) {
			bindingResult.rejectValue("email", "error.user",
					"The email is already registered, please use another one.");
		}
		if (userEntityServiceImpl.findByEmail(userRegistration.getEmail()).isPresent()) {
			bindingResult.rejectValue("username", "error.user",
					"There is already a user registered with the username provided.");
		}

		ModelAndView modelAndView = new ModelAndView();

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("/registration");
			return modelAndView;
		} else {
			UserEntity userEntity = new UserEntity();
			userEntity.setFirstName(userRegistration.getFirstName());
			userEntity.setLastName(userRegistration.getLastName());
			userEntity.setUsername(userRegistration.getUsername());
			userEntity.setEmail(userRegistration.getEmail());
			userEntity.setPassword(userRegistration.getPassword());

			Set<Role> userRoles = roleRepository.findRolesByNameSet(userRegistration.getRoles());
			userEntity.setRoles(userRoles);

			userEntityServiceImpl.createNewUserEntity(userEntity);

			modelAndView.addObject("successMessage", "Username " + userEntity.getUsername()
					+ " has been registered successfully. Now you should login for shopping.");
			
			modelAndView.addObject("userView", userRegistration);
			
			modelAndView.setViewName("/registration");
			return modelAndView;
		}
	}
}
