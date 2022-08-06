package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : JULIEN BARONI
 *
 * <p>
 * Controller permettant d'atteindre les URLs en lien avec les entités RuleName dans l'application.
 * <p>
 */
@Slf4j
@Controller
public class RuleNameController {
    @Autowired
    RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        List<RuleName> ruleNamelist = ruleNameService.findAllRuleNames();
        model.addAttribute("ruleNamelist", ruleNamelist);
        log.debug("ruleName : affichage de la liste");
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRatingForm(RuleName ruleName) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ruleNameService.saveRuleName(ruleName);
            model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
            log.debug("ruleName : erreur lors de l'ajout");
            return "redirect:/ruleName/list";
        }
        log.debug("ruleName : succès lors de l'ajout");
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        RuleName ruleName = ruleNameService.findRuleNameById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRating(@PathVariable("id") Long id, @Valid RuleName ruleName,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.debug("ruleName : erreur lors de la modification");
            return "redirect:/ruleName/list";
        }
        ruleName.setRuleNameId(id);
        ruleNameService.saveRuleName(ruleName);
        model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
        log.debug("ruleName : succès lors de la modification");
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRating(@PathVariable("id") Long id, Model model) {
        RuleName ruleNameToDelete = ruleNameService.findRuleNameById(id);
        ruleNameService.deleteRuleName(ruleNameToDelete.getRuleNameId());
        model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
        log.debug("ruleName : succès lors de la suppression");
        return "redirect:/ruleName/list";
    }
}
