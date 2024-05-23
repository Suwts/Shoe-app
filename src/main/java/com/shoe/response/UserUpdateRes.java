package com.shoe.response;

import com.shoe.entity.Role;
import com.shoe.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserUpdateRes {
    private String user_name;
    private String email;
    private String sex;
    private String phone_number;
    private String address;
    private String full_name;
    public static UserUpdateRes fromUser(User user){
        return UserUpdateRes.builder()
                .user_name(user.getUserNames())
                .email(user.getEmail())
                .sex(user.getSex())
                .phone_number(user.getPhoneNumber())
                .address(user.getAddress())
                .full_name(user.getFullName())
                .build();
    }
}
