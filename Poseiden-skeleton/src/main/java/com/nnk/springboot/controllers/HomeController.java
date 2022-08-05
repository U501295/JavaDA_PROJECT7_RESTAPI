package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@Controller
public class HomeController {
    @RolesAllowed({"USER", "ADMIN"})
    @RequestMapping("/home")
    public String getGithub() {
        return "home";
    }


}
