package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.RuleNameService;
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

@Controller
public class RuleNameController {
    @Autowired
    RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    // call service find all ruleNames to show to the view
    public String home(Model model) {
        List<RuleName> ruleNamelist = ruleNameService.findAllRuleNames();
        model.addAttribute("ruleNamelist", ruleNamelist);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRatingForm(RuleName ruleName) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    //TODO : voir si ça crée pas deux objets à la place d'un seul
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model)
        // check data valid and save to db, after saving return ruleName list
    /*public String validate(@RequestParam("account") String account, @RequestParam("type") String type,
                           @RequestParam("ruleNameQuantity") double ruleNameQuantity, Model model)*/ {
        if (!result.hasErrors()) {
            //RuleName newRating = new RuleName(ruleName.getAccount(), ruleName.getType(), ruleName.getRatingQuantity());
            ruleNameService.saveRuleName(ruleName);
            model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        // get RuleName by Id and to model then show to the form
        RuleName ruleName = ruleNameService.findRuleNameById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRating(@PathVariable("id") Long id, @Valid RuleName ruleName,
                               BindingResult result, Model model) {
        // check required fields, if valid call service to update RuleName and return list RuleName
        if (result.hasErrors()) {
            return "redirect:ruleName/list";
        }
        ruleName.setRuleNameId(id);
        ruleNameService.saveRuleName(ruleName);
        model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRating(@PathVariable("id") Long id, Model model) {
        // Find RuleName by Id and delete the ruleName, return to RuleName list
        RuleName ruleNameToDelete = ruleNameService.findRuleNameById(id);
        ruleNameService.deleteRuleName(ruleNameToDelete.getRuleNameId());
        model.addAttribute("ruleNames", ruleNameService.findAllRuleNames());
        return "redirect:/ruleName/list";
    }
}
