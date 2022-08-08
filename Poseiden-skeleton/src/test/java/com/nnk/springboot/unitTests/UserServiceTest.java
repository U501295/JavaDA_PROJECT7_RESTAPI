package com.nnk.springboot.unitTests;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
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
public class UserServiceTest {


    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    public void setup() {
        user = new User("username", "password", "fullname", "role");
        user.setId(1l);
    }

    @Test
    public void should_returnSomething_whenGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        Assertions.assertThat(userService.findAllUsers()).isNotNull();
    }

    @Test
    public void should_saveUser() {
        when(userRepository.save(any())).thenReturn(user);

        User userEntity = userService.saveUser(user);

        Assertions.assertThat(userEntity).isNotNull();
        verify(userRepository, times(1)).save(any());
    }


    @Test
    public void should_findUser_whenGetExistingUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User userEntity = userService.findUserById(anyLong());

        Assertions.assertThat(userEntity).isNotNull();
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    public void should_throwIllegalArgumentException_whenGetExistingUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userService.findUserById(anyLong()));
    }

    @Test
    public void should_deleteUser_whenDeleteExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        userService.deleteUser(anyLong());

        verify(userRepository, times(1)).delete(Optional.of(user).get());
    }

    @Test
    public void should_throwNoSuchElementException_whenDeleteExistingUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> userService.deleteUser(anyLong()));
    }

}
