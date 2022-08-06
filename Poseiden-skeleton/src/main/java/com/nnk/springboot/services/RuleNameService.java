package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : JULIEN BARONI
 *
 * <p>
 * Service permettant d'effectuer les actions CRUDs sur les entités ruleName dans l'application.
 * <p>
 */
@Slf4j
@Service
public class RuleNameService {
    @Autowired
    private RuleNameRepository ruleNameRepository;

    public List<RuleName> findAllRuleNames() {
        List<RuleName> ruleNames = ruleNameRepository.findAll();
        log.debug("ruleName : get all");
        return ruleNames;
    }

    public RuleName saveRuleName(RuleName ruleName) {
        log.debug("ruleName : save");
        return ruleNameRepository.save(ruleName);
    }

    public RuleName findRuleNameById(Long id) {
        log.debug("ruleName : find by id");
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        return ruleName;
    }

    public void deleteRuleName(Long id) {
        log.debug("ruleName : delete");
        RuleName ruleName = ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
        ruleNameRepository.delete(ruleName);

    }
}
