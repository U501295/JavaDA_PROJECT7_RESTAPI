package com.nnk.springboot.unitTests;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {


    @InjectMocks
    private RuleNameService ruleNameService;
    @Mock
    private RuleNameRepository ruleNameRepository;
    private RuleName ruleName;

    @BeforeEach
    public void setup() {
        ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        ruleName.setRuleNameId(1l);
    }

    @Test
    public void should_returnSomething_whenGetAllRuleNames() {
        when(ruleNameRepository.findAll()).thenReturn(Collections.singletonList(ruleName));

        Assertions.assertThat(ruleNameService.findAllRuleNames()).isNotNull();
    }

    @Test
    public void should_saveRuleName() {
        when(ruleNameRepository.save(any())).thenReturn(ruleName);

        RuleName RuleNameEntity = ruleNameService.saveRuleName(ruleName);

        Assertions.assertThat(RuleNameEntity).isNotNull();
        verify(ruleNameRepository, times(1)).save(any());
    }


    @Test
    public void should_findRuleName_whenGetExistingRuleNameById() {
        when(ruleNameRepository.findById(anyLong())).thenReturn(Optional.of(ruleName));

        RuleName RuleNameEntity = ruleNameService.findRuleNameById(anyLong());

        Assertions.assertThat(RuleNameEntity).isNotNull();
        verify(ruleNameRepository, times(1)).findById(anyLong());
    }

    @Test
    public void should_throwIllegalArgumentException_whenGetExistingRuleNameById() {
        when(ruleNameRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> ruleNameService.findRuleNameById(anyLong()));
    }

    @Test
    public void should_deleteRuleName_whenDeleteExistingRuleName() {
        when(ruleNameRepository.findById(anyLong())).thenReturn(Optional.of(ruleName));

        ruleNameService.deleteRuleName(anyLong());

        verify(ruleNameRepository, times(1)).delete(Optional.of(ruleName).get());
    }

    @Test
    public void should_throwNoSuchElementException_whenDeleteExistingRuleName() {
        when(ruleNameRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> ruleNameService.deleteRuleName(anyLong()));
    }

}
