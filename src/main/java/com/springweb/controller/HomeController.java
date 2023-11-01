package com.springweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String home(Model model) {
	    model.addAttribute("webTech", "Spring Web MVC security via Spring Boot 3.1.x");
	    model.addAttribute("projectName", "Simple shopping cart with security 6.1.x");
	    return "/home";
	}
}


