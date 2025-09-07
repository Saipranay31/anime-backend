package com.anime.catalog.service;

import com.anime.catalog.dto.UserDTO;
import com.anime.catalog.model.User;
import com.anime.catalog.dto.SignupRequest;
import com.anime.catalog.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {
    void registerUser(SignupRequest signupRequest);
    User findByUsername(String username);
    User findByEmail(String email);
    List<UserDTO> getAllUsers();  // new method
    UserDTO updateUser(Long id, UserUpdateRequest request); // new
    void deleteUser(Long id); // new
}
