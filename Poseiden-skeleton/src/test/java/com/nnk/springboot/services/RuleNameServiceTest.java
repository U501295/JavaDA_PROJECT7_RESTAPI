package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleNameServiceTest {

    @Autowired
    private RuleNameService ruleNameService;

    @Test(expected = IllegalArgumentException.class)
    public void ruleNameTest() {

        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

        // Save
        rule = ruleNameService.saveRuleName(rule);
        Assert.assertNotNull(rule.getRuleNameId());
        Assert.assertEquals(rule.getJson(), "Json", "Json");

        // Update
        rule.setJson("modifiedJson");
        rule = ruleNameService.saveRuleName(rule);
        Assert.assertEquals(rule.getJson(), "modifiedJson", "modifiedJson");

        // FindAll
        List<RuleName> listResult = ruleNameService.findAllRuleNames();
        Assert.assertTrue(listResult.size() > 0);

        // FindOne
        Long id = rule.getRuleNameId();
        RuleName ruleName = ruleNameService.findRuleNameById(id);
        Assert.assertTrue(ruleName.getRuleNameId() > 0);

        // Delete
        ruleNameService.deleteRuleName(id);
        RuleName ruleNameException = ruleNameService.findRuleNameById(id);

    }


}
