package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.User;

import java.util.List;

public interface UserService {

    User addUser(UserDto userDto);

    List<User> getAllUsers();

    User loginUser(UserDto userDto);

    void deleteUser(Integer userId);
}
