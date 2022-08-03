package com.nnk.springboot.controllers;

import com.nnk.springboot.security.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

@Controller
public class HomeController {
    /*@RolesAllowed("USER")
    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }*/

    @RolesAllowed("[ADMIN]")
    @RequestMapping("/admin")
    public String adminHome() {
        if (SecurityUtils.isAdminConnected()) {
            return "redirect:/bidList/list";
        } else {
            return "/403";
        }

    }

    @RolesAllowed({"USER", "ADMIN"})
    @RequestMapping("/home")
    public String getGithub() {
        String username = SecurityUtils.getUserName();
        return "home";
    }


}
