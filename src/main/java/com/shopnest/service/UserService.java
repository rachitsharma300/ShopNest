package com.shopnest.service;

import com.shopnest.dto.UserDTO;
import com.shopnest.dto.LoginRequest;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO authenticate(LoginRequest loginRequest);  // ✅ Ye method add karo
    UserDTO getUserById(Long userId);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long userId);
}