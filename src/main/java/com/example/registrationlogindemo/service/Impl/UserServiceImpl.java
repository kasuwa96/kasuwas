package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(UserDto userDto) {
        try {
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setUserType(userDto.getUserType());

            return userRepository.save(user);
        } catch (Exception e) {
            // Log the error for debugging purposes
            e.printStackTrace();
            throw new RuntimeException("Failed to add user: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public User loginUser(UserDto userDto) {
        return userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
    }
}
