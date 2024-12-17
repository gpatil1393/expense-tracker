package com.cd.controllers;

import com.cd.models.User;
import com.cd.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    private User user;

    private int userId;

    @BeforeEach
    void setUp() {
        userId = 1;
        user = new User();
        user.setId(userId);
        user.setFirstName("Gaurav");
        user.setLastName("Patil");
        user.setUsername("gpatil");
        user.setPassword("Welcome123$");
        user.setEmail("gaurav.patil@nitorinfotech.com");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createUser() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(userService.createUser(user)).thenReturn(user);
        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());

        verify(userService).createUser(user);
    }

    @Test
    void getUserById() {
        when(userService.findUserById(userId)).thenReturn(user);

        ResponseEntity<User> response = userController.getUserById(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
        assertEquals(userId, Objects.requireNonNull(response.getBody()).getId());
        verify(userService).findUserById(userId);
    }
}