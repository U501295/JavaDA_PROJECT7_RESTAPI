package com.nnk.springboot.controller.front;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.helper.TestFunctions;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)
@AutoConfigureMockMvc//(addFilters = false)
//@WebMvcTest(controllers = RuleNameRestController.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RuleNameControllerTest {

    private final RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
    @Autowired
    public MockMvc mockMvc;
    @MockBean
    RuleNameService ruleNameService;

    @Test
    @WithMockUser
    public void testRuleNameList() throws Exception {
        when(ruleNameService.findAllRuleNames()).thenReturn(Collections.singletonList(rule));
        mockMvc.perform(get("/ruleName/list").with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleNamelist"))
                .andExpect(content().string(containsString(String.valueOf(rule.getName()))));
    }

    @Test
    @WithMockUser
    public void addRuleName() throws Exception {
        mockMvc.perform(
                        get("/ruleName/add")
                                .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andDo(print());

    }

    @Test
    @WithMockUser
    void testValidate() throws Exception {
        mockMvc.perform(post("/ruleName/validate").with(csrf().asHeader())
                        .param("name", "name")
                        .param("description", "description")
                        .param("json", "json")
                        .param("template", "template")
                        .param("sqlStr", "sqlStr")
                        .param("sqlPart", "sqlPart"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/ruleName/list"));
    }


    @Test
    @WithMockUser
    public void Error403ValidateRuleName() throws Exception {
        String json = TestFunctions.asJsonString(rule);
        mockMvc.perform(
                post("http://localhost:8080/ruleName/validate")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)


        ).andExpect(status().isForbidden()).andDo(print());

    }

    @Test
    @WithMockUser
    void testValidateHasError() throws Exception {
        mockMvc.perform(post("/ruleName/validate").with(csrf().asHeader())
                        .param("name", "")
                        .param("description", "description")
                        .param("json", "json")
                        .param("template", "template")
                        .param("sqlStr", "sqlStr")
                        .param("sqlPart", "sqlPart"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("ruleName", "name", "NotBlank"));
    }


    @Test
    @WithMockUser
    public void RuleNameUpdate() throws Exception {
        when(ruleNameService.findRuleNameById(anyLong())).thenReturn(rule);

        mockMvc.perform(get("/ruleName/update/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithMockUser
    void testUpdateRuleName() throws Exception {
        when(ruleNameService.saveRuleName(rule)).thenReturn(rule);
        mockMvc.perform(post("/ruleName/update/1").with(csrf().asHeader())
                        .param("name", "name")
                        .param("description", "description")
                        .param("json", "json")
                        .param("template", "template")
                        .param("sqlStr", "sqlStr")
                        .param("sqlPart", "sqlPart"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(model().hasNoErrors());

    }

    @Test
    @WithMockUser
        //TODO : regarder plus en détail
    void testUpdateRuleNameHasError() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                        .with(csrf().asHeader())
                        .param("name", "")
                        .param("description", "description")
                        .param("json", "json")
                        .param("template", "template")
                        .param("sqlStr", "sqlStr")
                        .param("sqlPart", "sqlPart"))
                .andExpect(view().name("redirect:/ruleName/list"));

    }

    @Test
    @WithMockUser
    void testDeleteRuleName() throws Exception {
        when(ruleNameService.findRuleNameById(anyLong())).thenReturn(rule);
        mockMvc.perform(get("/ruleName/delete/0").with(csrf().asHeader()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/ruleName/list"));
    }

}
