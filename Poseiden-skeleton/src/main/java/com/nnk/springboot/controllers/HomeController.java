package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@Controller
public class HomeController
{
	@RolesAllowed("USER")
	@RequestMapping("/")
	public String home(Model model)
	{
		return "home";
	}

	@RolesAllowed({"USER","ADMIN"})
	@RequestMapping("/admin")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}

	@RolesAllowed({"USER","ADMIN"})
	@RequestMapping("/*")
	public String getGithub()
	{
		return "Welcome Github user!";
	}


}
