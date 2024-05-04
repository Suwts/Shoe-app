package com.shoe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shoe.entity.Role;
import com.shoe.entity.User;
import com.shoe.reponsitory.UserRepo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes{
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String email;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("user_name")
    private String userName;
    private String password;
    private String re_password;
    private String sex;
    private int role_id;
    private int active = 1;
    private Role role;
    private User user;
}
