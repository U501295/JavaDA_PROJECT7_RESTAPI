package com.nnk.springboot.TestIT.repository;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleIT {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    public void ruleTest() {
        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

        // Save
        rule = ruleNameRepository.save(rule);
        Assert.assertNotNull(rule.getRuleNameId());
        Assertions.assertThat(rule.getName()).isEqualTo("Rule Name");
        //Assert.assertTrue(rule.getName().equals("Rule Name"));

        // Update
        rule.setName("Rule Name Update");
        rule = ruleNameRepository.save(rule);
        Assertions.assertThat(rule.getName()).isEqualTo("Rule Name Update");
        //Assert.assertTrue(rule.getName().equals("Rule Name Update"));

        // Find
        List<RuleName> listResult = ruleNameRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Long id = rule.getRuleNameId();
        ruleNameRepository.delete(rule);
        Optional<RuleName> ruleList = ruleNameRepository.findById(id);
        Assert.assertFalse(ruleList.isPresent());
    }
}
