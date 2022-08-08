package com.nnk.springboot.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.User;
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
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @SuppressWarnings("unused")
    @MockBean
    private PasswordEncoder passwordEncoder;
    private User user;

    @BeforeEach
    public void setup() {
        user = new User("username", "password", "fullname", "role");
        user.setId(1l);
    }

    @Test
    public void should_returnRightUserList_whenGetUser() throws Exception {
        when(userService.findAllUsers()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(user.getUsername()))));
    }

    @Test
    public void should_returnRightUser_whenGetUserId() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.valueOf(user.getUsername()))));
    }

    @Test
    public void should_returnNotFound_whenGetMissingUserId() throws Exception {
        when(userService.findUserById(anyLong())).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("error")));
    }

    @Test
    public void should_returnBindingErrors_whenPostPartialUser() throws Exception {
        User userEntityPartial = user;
        userEntityPartial.setUsername("");
        String inputJson = new ObjectMapper().writeValueAsString(userEntityPartial);

        mockMvc.perform(post("/api/user")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_createUser_whenPostNewUserwithRightPassWordFormat() throws Exception {
        user.setPassword("GoodPassword1!");
        when(userService.saveUser(any())).thenReturn(user);
        String inputJson = new ObjectMapper().writeValueAsString(user);

        mockMvc.perform(post("/api/user")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldnot_createUser_whenPostNewUserwithWrongPassWordFormat() throws Exception {
        when(userService.saveUser(any())).thenReturn(user);
        String inputJson = new ObjectMapper().writeValueAsString(user);

        mockMvc.perform(post("/api/user")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_returnBindingErrors_whenPutPartialUser() throws Exception {
        User userEntityPartial = user;
        userEntityPartial.setUsername("");
        String inputJson = new ObjectMapper().writeValueAsString(userEntityPartial);

        mockMvc.perform(put("/api/user/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_updateUser_whenPutExistingUserwithRightPassWordFormat() throws Exception {
        user.setPassword("GoodPassword1!");
        when(userService.saveUser(any())).thenReturn(user);
        String inputJson = new ObjectMapper().writeValueAsString(user);

        mockMvc.perform(put("/api/user/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void shouldnot_updateUser_whenPutExistingUserwithWrongPassWordFormat() throws Exception {
        when(userService.saveUser(any())).thenReturn(user);
        String inputJson = new ObjectMapper().writeValueAsString(user);

        mockMvc.perform(put("/api/user/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_notUpdateUser_whenPutMissingUser() throws Exception {
        when(userService.saveUser(any())).thenThrow(IllegalArgumentException.class);
        String inputJson = new ObjectMapper().writeValueAsString(user);

        mockMvc.perform(put("/api/user/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_notUpdateUser_whenPutDuplicatedUser() throws Exception {
        when(userService.saveUser(any())).thenThrow(IllegalArgumentException.class);
        String inputJson = new ObjectMapper().writeValueAsString(user);

        mockMvc.perform(put("/api/user/1")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void should_deleteUser_whenDeleteExistingUser() throws Exception {
        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_NotDeleteUser_whenDeleteMissingUser() throws Exception {
        doThrow(new IllegalArgumentException()).when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isNotFound());
    }
}
