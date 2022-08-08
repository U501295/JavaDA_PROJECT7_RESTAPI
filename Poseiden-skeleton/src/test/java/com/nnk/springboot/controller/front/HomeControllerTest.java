package com.nnk.springboot.controller.front;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc//(addFilters = false)
//@WebMvcTest(controllers = curvePointController.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class HomeControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Test
    @WithMockUser
    public void testHome() throws Exception {
        mockMvc.perform(get("/home").with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

}