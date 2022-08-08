package com.nnk.springboot.controllers.api;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/rule")

public class RuleNameRestController {
    @Autowired
    private RuleNameService ruleNameService;


    @GetMapping()
    public List<RuleName> getRuleNames() {
        log.debug("get all ruleNames");
        return ruleNameService.findAllRuleNames();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRuleName(@PathVariable Long id) {
        try {
            RuleName ruleNameEntity = ruleNameService.findRuleNameById(id);
            log.debug("successfully get rule/" + id);
            return ResponseEntity.status(HttpStatus.OK).body(ruleNameEntity);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while getting rule because of missing rule with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logAndBodyMessage);
        }
    }

    @PostMapping()
    public ResponseEntity<?> postRuleName(@Valid @RequestBody RuleName ruleNameEntity,
                                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String logAndBodyMessage = "error while posting rule because of wrong input data : "
                    + bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).
                    collect(Collectors.joining(", "));
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }

        RuleName ruleNameEntitySaved = ruleNameService.saveRuleName(ruleNameEntity);
        log.debug("successfully post rule");
        return ResponseEntity.status(HttpStatus.CREATED).body(ruleNameEntitySaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putRuleName(@PathVariable Long id,
                                         @Valid @RequestBody RuleName ruleNameEntity,
                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String logAndBodyMessage = "error while putting rule because of wrong input data : "
                    + bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).
                    collect(Collectors.joining(", "));
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }

        try {
            RuleName ruleNameEntityToModify = ruleNameService.findRuleNameById(id);
            ruleNameEntityToModify = ruleNameEntity;
            ruleNameEntityToModify.setRuleNameId(id);
            RuleName ruleNameSaved = ruleNameService.saveRuleName(ruleNameEntity);
            log.debug("successfully put rule/" + id);
            return ResponseEntity.status(HttpStatus.OK).body(ruleNameSaved);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while putting rule because missing rule with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(logAndBodyMessage);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRuleName(@PathVariable Long id) {
        try {
            ruleNameService.deleteRuleName(id);
            String logAndBodyMessage = "successfully delete rule/" + id;
            log.debug(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.OK).body(logAndBodyMessage);
        } catch (IllegalArgumentException e) {
            String logAndBodyMessage = "error while deleting rule because of missing rule with id=" + id;
            log.error(logAndBodyMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logAndBodyMessage);
        }
    }
}
