package com.shopnest.controller;

import com.shopnest.dto.UserDTO;
import com.shopnest.dto.LoginRequest;
import com.shopnest.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testShowLoginForm() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }

    @Test
    void testShowRegisterForm() throws Exception {
        mockMvc.perform(get("/auth/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"));
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");
        userDTO.setFullName("Test User");

        when(userService.registerUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/auth/register")
                        .param("email", "test@example.com")
                        .param("password", "password")
                        .param("fullName", "Test User"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"))
                .andExpect(model().attributeExists("success"));
    }

    @Test
    void testLogin_Success() throws Exception {
        UserDTO userDTO = new UserDTO(1L, "test@example.com", "Test User", "CUSTOMER");
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password");

        when(userService.authenticate(any(LoginRequest.class))).thenReturn(userDTO);

        mockMvc.perform(post("/auth/login")
                        .param("email", "test@example.com")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}