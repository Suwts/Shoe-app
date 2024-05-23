package com.shoe.response;

import com.shoe.entity.Role;
import com.shoe.entity.User;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private int userId;
    private String user_name;
    private String email;
    private String sex;
    private String full_name;
    private String phone_number;
    private String address;
    private int active;
    private Role role;
    private List<User> users;
    private int totalPage;
    public static UserResponse fromUser(User user){
        return UserResponse.builder()
                .userId(user.getUserID())
                .user_name(user.getUserNames())
                .email(user.getEmail())
                .sex(user.getSex())
                .phone_number(user.getPhoneNumber())
                .address(user.getAddress())
                .active(user.getActive())
                .full_name(user.getFullName())
                .role(user.getRole()).build();
    }

    public static UserResponse pageUser(List<User> user, int totalPage){
        return UserResponse.builder()
                .users(user)
                .totalPage(totalPage)
                .build();
    }

}
