package com.nnk.springboot.controller;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.helper.TestFunctions;
import com.nnk.springboot.services.UserService;
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
//@WebMvcTest(controllers = UserListController.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    private final User user = new User("username", "password", "fullname", "role");
    @Autowired
    public MockMvc mockMvc;
    @MockBean
    UserService userService;

    @Test
    @WithMockUser
    public void testUserListList() throws Exception {
        when(userService.findAllUsers()).thenReturn(Collections.singletonList(user));
        mockMvc.perform(get("/user/list").with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("userlist"))
                .andExpect(content().string(containsString(String.valueOf(user.getUsername()))));
    }

    @Test
    @WithMockUser
    public void addUserList() throws Exception {
        mockMvc.perform(
                        get("/user/add")
                                .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andDo(print());

    }

    @Test
    @WithMockUser
    void testValidate() throws Exception {
        mockMvc.perform(post("/user/validate").with(csrf().asHeader())
                        .param("username", "name")
                        .param("password", "password")
                        .param("fullname", "full")
                        .param("role", "role"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"));
    }


    @Test
    @WithMockUser
    public void Error403ValidateUserList() throws Exception {
        String json = TestFunctions.asJsonString(user);
        mockMvc.perform(
                post("http://localhost:8080/user/validate")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)


        ).andExpect(status().isForbidden()).andDo(print());

    }

    @Test
    @WithMockUser
    void testValidateHasError() throws Exception {
        mockMvc.perform(post("/user/validate").with(csrf().asHeader())
                        .param("username", "")
                        .param("password", "password")
                        .param("fullname", "full")
                        .param("role", "role"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("user", "account", "NotBlank"));
    }


    @Test
    @WithMockUser
    public void UserListUpdate() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/user/update/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser
    void testUpdateUser() throws Exception {
        when(userService.saveUser(user)).thenReturn(user);
        mockMvc.perform(post("/user/update/1").with(csrf().asHeader())
                        .param("username", "name")
                        .param("password", "password")
                        .param("fullname", "full")
                        .param("role", "role"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(model().hasNoErrors());

    }

    @Test
    @WithMockUser
        //TODO : regarder plus en détail
    void testUpdateUserHasError() throws Exception {
        mockMvc.perform(post("/user/update/1")
                        .with(csrf().asHeader())
                        .param("username", "")
                        .param("password", "password")
                        .param("fullname", "full")
                        .param("role", "role"))
                .andExpect(view().name("redirect:/user/list"));

    }

    @Test
    @WithMockUser
    void testDeleteUser() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(user);
        mockMvc.perform(get("/user/delete/0").with(csrf().asHeader()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"));
    }

}
