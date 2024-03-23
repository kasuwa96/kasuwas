package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Appointment;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add/users")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        User user = userService.addUser(userDto);
        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.CREATED);
    }


    @GetMapping("/getusers")
    public ResponseEntity<List<User>> getAllAppointments() {
        List<User> userList = userService.getAllUsers();
        System.out.println("userlist"+userList);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }


}
