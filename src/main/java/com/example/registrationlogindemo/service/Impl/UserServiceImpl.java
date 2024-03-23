package com.example.registrationlogindemo.service.Impl;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.PatientRepository;
import com.example.registrationlogindemo.repository.UserRepository;
import com.example.registrationlogindemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public User addUser(UserDto userDto) {
        try {
            // Check if the email already exists in the user table
            Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
            if (existingUser.isPresent()) {
                throw new IllegalArgumentException("Email already exists");
            }

            // Check if the email exists in the patient table
            boolean emailExistsInPatientTable = patientRepository.existsByEmail(userDto.getEmail());
            if (emailExistsInPatientTable) {
                throw new IllegalArgumentException("Email already exists in the patient table");
            }

            // If email does not exist in both tables, proceed with adding the user
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setUserType(userDto.getUserType());

            return userRepository.save(user);
        } catch (IllegalArgumentException e) {
            // Re-throw the exception to be handled at the controller level
            throw e;
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

    @Override
    public void deleteUser(Integer userId) { // Change parameter type to Integer
        userRepository.deleteById(userId);
    }
}
