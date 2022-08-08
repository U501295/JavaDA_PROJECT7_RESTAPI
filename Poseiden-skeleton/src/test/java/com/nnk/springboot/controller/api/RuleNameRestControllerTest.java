package com.nnk.springboot.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc//(addFilters = false)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RuleNameRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNamePointService;

    @SuppressWarnings("unused")
    @MockBean
    private UserService userService;
    @SuppressWarnings("unused")
    @MockBean
    private PasswordEncoder passwordEncoder;
    private RuleName rule;

    @BeforeEach
    public void setup() {
        rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        rule.setRuleNameId(1l);
    }

    @Test
    public void should_returnRightRuleNameList_whenGetRuleName() throws Exception {
        when(ruleNamePointService.findAllRuleNames()).thenReturn(Collections.singletonList(rule));

        mockMvc.perform(get("/api/rule"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(rule.getName()))));
    }

    @Test
    public void should_returnRightRuleName_whenGetRuleNameId() throws Exception {
        when(ruleNamePointService.findRuleNameById(anyLong())).thenReturn(rule);

        mockMvc.perform(get("/api/rule/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(rule.getName()))));
    }

    @Test
    public void should_returnNotFound_whenGetMissingRuleNameId() throws Exception {
        when(ruleNamePointService.findRuleNameById(anyLong())).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/api/rule/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("error")));
    }

    @Test
    public void should_returnBindingErrors_whenPostPartialRuleName() throws Exception {
        RuleName ruleNameEntityPartial = rule;
        ruleNameEntityPartial.setName("");
        String inputJson = new ObjectMapper().writeValueAsString(ruleNameEntityPartial);

        mockMvc.perform(post("/api/rule")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_createRuleName_whenPostNewRuleName() throws Exception {
        when(ruleNamePointService.saveRuleName(any())).thenReturn(rule);
        String inputJson = new ObjectMapper().writeValueAsString(rule);

        mockMvc.perform(post("/api/rule")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_returnBindingErrors_whenPutPartialRuleName() throws Exception {
        RuleName ruleNameEntityPartial = rule;
        ruleNameEntityPartial.setName("");
        String inputJson = new ObjectMapper().writeValueAsString(ruleNameEntityPartial);

        mockMvc.perform(put("/api/rule/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_updateRuleName_whenPutExistingRuleName() throws Exception {
        when(ruleNamePointService.saveRuleName(any())).thenReturn(rule);
        String inputJson = new ObjectMapper().writeValueAsString(rule);

        mockMvc.perform(put("/api/rule/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void should_notUpdateRuleName_whenPutMissingRuleName() throws Exception {
        when(ruleNamePointService.saveRuleName(any())).thenThrow(IllegalArgumentException.class);
        String inputJson = new ObjectMapper().writeValueAsString(rule);

        mockMvc.perform(put("/api/rule/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_notUpdateRuleName_whenPutDuplicatedRuleName() throws Exception {
        when(ruleNamePointService.saveRuleName(any())).thenThrow(IllegalArgumentException.class);
        String inputJson = new ObjectMapper().writeValueAsString(rule);

        mockMvc.perform(put("/api/rule/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_deleteRuleName_whenDeleteExistingRuleName() throws Exception {
        mockMvc.perform(delete("/api/rule/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_NotDeleteRuleName_whenDeleteMissingRuleName() throws Exception {
        doThrow(new IllegalArgumentException()).when(ruleNamePointService).deleteRuleName(anyLong());

        mockMvc.perform(delete("/api/rule/1"))
                .andExpect(status().isNotFound());
    }
}
