package com.springapp.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HelloController {

	@RequestMapping("/")
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Registration");
		return "registration";
	}

	//Test to another request.
	@RequestMapping("/myhello")
	public String printWelcome1(ModelMap model) {
		model.addAttribute("message1", "Hello world myhello!!");
		return "myhello";
	}
}