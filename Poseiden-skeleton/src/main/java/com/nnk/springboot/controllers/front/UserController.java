package com.nnk.springboot.controllers.front;

import com.nnk.springboot.configuration.PasswordConstraintValidator;
import com.nnk.springboot.configuration.SecurityUtils;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;

/**
 * @author : JULIEN BARONI
 *
 * <p>
 * Controller permettant d'atteindre les URLs en lien avec les entités user dans l'application.
 * <p>
 */

@Slf4j
@Controller
public class UserController {
    ConstraintValidatorContext context;
    @Autowired
    private UserService userService;
    
    private PasswordConstraintValidator passwordConstraintValidator;

    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("sec", SecurityUtils.isAdminConnected());
        log.debug("user : affichage de la liste");
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.saveUser(user);
            model.addAttribute("users", userService.findAllUsers());
            log.debug("user : erreur lors de l'ajout");
            return "redirect:/user/list";
        }
        log.debug("user : succès lors de l'ajout");
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid @ModelAttribute User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("user : erreur lors de la modification");
            return "redirect:/user/list";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        boolean valid = passwordConstraintValidator.isValid(user.getPassword(), context);
        User userModified = userService.saveUser(user);
        model.addAttribute("users", userService.findAllUsers());
        log.debug("user : succès lors de la modification");
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        userService.deleteUser(id);
        model.addAttribute("users", userService.findAllUsers());
        log.debug("user : succès lors de la suppression");
        return "redirect:/user/list";
    }
}
