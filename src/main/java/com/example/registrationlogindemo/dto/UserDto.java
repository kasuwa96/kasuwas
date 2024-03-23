package com.example.registrationlogindemo.dto;

import com.example.registrationlogindemo.entity.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String email;
    private String password;
    private String userType;

    public static UserDto fromUser(User user) {
        return new UserDto(user.getEmail(), user.getPassword(), user.getUserType());
    }

//    public static List<UserDto> fromUserList(List<User> userList) {
//        return userList.stream().map(UserDto::fromUser).collect(Collectors.toList());
//    }
}
