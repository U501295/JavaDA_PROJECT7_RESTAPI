package com.nnk.springboot.controllers.front;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

/**
 * @author : JULIEN BARONI
 *
 * <p>
 * Controller permettant d'atteindre les URLs en lien avec la page d'accueil dans l'application.
 * <p>
 */
@Slf4j
@Controller
public class HomeController {
    @RolesAllowed({"USER", "ADMIN"})
    @RequestMapping("/home")
    public String getGithub() {
        log.debug("home : success dans l'atteinte de la page d'accueil");
        return "home";
    }


}
