package com.nnk.springboot.controller.front;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
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
    public void testUserList() throws Exception {
        when(userService.findAllUsers()).thenReturn(Collections.singletonList(user));
        mockMvc.perform(get("/user/list").with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void addUserList() throws Exception {
        mockMvc.perform(
                        get("/user/add")
                                .with(csrf().asHeader()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void testValidate() throws Exception {
        when(userService.saveUser(any(User.class))).thenReturn(user);
        mockMvc.perform(post("/user/validate").with(csrf().asHeader())
                        .param("username", "name")
                        .param("password", "Password1!")
                        .param("fullname", "full")
                        .param("role", "role"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void testValidate_shouldShowErrorMessageWhenWrongpasswordFormat() throws Exception {
        when(userService.saveUser(any(User.class))).thenReturn(user);
        mockMvc.perform(post("/user/validate").with(csrf().asHeader())
                        .param("username", "name")
                        .param("password", "pass")
                        .param("fullname", "full")
                        .param("role", "role"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("user", "password", "ValidPassword"))
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void testValidateHasError() throws Exception {
        mockMvc.perform(post("/user/validate").with(csrf().asHeader())
                        .param("username", "")
                        .param("password", "password")
                        .param("fullname", "full")
                        .param("role", "role"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrorCode("user", "username", "NotBlank"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"USER"})
    void testValidateHasError_whenUserTriesToCreateANewUser() throws Exception {
        mockMvc.perform(post("/user/validate").with(csrf().asHeader())
                        .param("username", "user")
                        .param("password", "Password1!")
                        .param("fullname", "full")
                        .param("role", "role"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void UserListUpdate() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(user);
        mockMvc.perform(get("/user/update/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void testUpdateUser() throws Exception {
        when(userService.saveUser(user)).thenReturn(user);
        mockMvc.perform(post("/user/update/2").with(csrf().asHeader())
                        .param("username", "name")
                        .param("password", "password")
                        .param("fullname", "full")
                        .param("role", "role"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(model().hasNoErrors());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void testUpdateUserHasError() throws Exception {
        when(userService.saveUser(user)).thenReturn(user);
        mockMvc.perform(post("/user/update/2")
                        .with(csrf().asHeader())
                        .param("username", "")
                        .param("password", "password")
                        .param("fullname", "full")
                        .param("role", "role"))
                .andExpect(view().name("redirect:/user/list"));

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void testDeleteUser() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(user);
        mockMvc.perform(get("/user/delete/0").with(csrf().asHeader()))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"));
    }

}
