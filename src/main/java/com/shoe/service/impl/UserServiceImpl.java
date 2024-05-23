package com.shoe.service.impl;

import com.shoe.Utils.JwtsUtils;
import com.shoe.Utils.Language;
import com.shoe.dto.PasswordDTO;
import com.shoe.dto.UserDTO;
import com.shoe.entity.Role;
import com.shoe.entity.User;
import com.shoe.exception.DataNotFound;
import com.shoe.reponsitory.RoleRepo;
import com.shoe.reponsitory.UserRepo;
import com.shoe.response.UserResponse;
import com.shoe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private JwtsUtils jwtsUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Language language;

    @Override
    public UserDTO signUp(UserDTO register) {
        UserDTO userDTO = new UserDTO();
        try{
            User user = new User();
            user.setEmail(register.getEmail());
            user.setUserNames(register.getUserName());
            user.setPassword(passwordEncoder.encode(register.getPassword()));
            user.setSex(register.getSex());
            user.setFullName(register.getFullName());
            user.setActive(register.getActive());
            user.setRoleid(2);
            Role role = roleRepo.findByRoleID(2).orElseThrow(() -> new DataNotFound(language.getLocale("data.not.found")));
            user.setRole(role);
            if(register.getPassword().equalsIgnoreCase(register.getRe_password()) && existsByEmail(register.getEmail()) == false){
                User u = userRepo.save(user);
                if(u != null && u.getUserID() > 0){
                    userDTO.setUser(u);
                    userDTO.setMessage(language.getLocale("user.register.successful"));
                    userDTO.setStatusCode(200);
                }
            }

        }catch (Exception e){
            userDTO.setStatusCode(500);
            userDTO.setError(e.getMessage());
        }
        return userDTO;
    }

    @Override
    public UserDTO signIn(UserDTO login) {
        UserDTO userDTO = new UserDTO();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
            var user = userRepo.findByEmail(login.getEmail()).orElseThrow();
            var jwt = jwtsUtils.generateToken(user);
            var refreshToken = jwtsUtils.generateRefeshToken(new HashMap<>(), user);
            userDTO.setStatusCode(200);
            userDTO.setToken(jwt);
            userDTO.setRefreshToken(refreshToken);
            userDTO.setMessage(language.getLocale("user.login.successful"));
        }catch (Exception e){
            userDTO.setStatusCode(500);
            userDTO.setError(e.getMessage());
        }
        return userDTO;
    }

    @Override
    public UserDTO refreshToken(UserDTO refresh) {
        UserDTO userDTO = new UserDTO();
        String email = jwtsUtils.extractUserName(refresh.getRefreshToken());
        User user = userRepo.findByEmail(refresh.getEmail()).orElseThrow();
        if(jwtsUtils.isTokenValid(refresh.getToken(), user)){
            var jwt = jwtsUtils.generateToken(user);
            userDTO.setStatusCode(200);
            userDTO.setToken(jwt);
            userDTO.setRefreshToken(refresh.getToken());
        }
        userDTO.setStatusCode(500);
        return userDTO;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public User getDetail(String token) throws Exception {
        if(jwtsUtils.isExpired(token)){
            throw new Exception("Token is expired");
        }
        String email = jwtsUtils.extractUserName(token);
        User user = userRepo.findByEmail(email).orElseThrow();
        return user;
    }

    @Override
    public User updateUser(int userId, UserDTO userDTO ,String auToken) throws Exception {
        String token = auToken.substring(7);
        User user1 = this.getDetail(token);
        if (user1.getUserID() != userId) {
            return null;
        }
        User user = userRepo.findByUserID(userId).orElseThrow();
        if(userDTO.getFullName() != null){
            user.setFullName(userDTO.getFullName());
        }
        if(userDTO.getUserName() != null){
            user.setUserNames(userDTO.getUserName());
        }
        if(userDTO.getEmail() != null){
            user.setSex(userDTO.getSex());
        }
        if(userDTO.getPhone_number() != null){
            user.setPhoneNumber(userDTO.getPhone_number());
        }
        if(userDTO.getAdress() != null){
            user.setAddress(userDTO.getAdress());
        }
        userRepo.save(user);
        return user;
    }

    @Override
    public Page<User> getUsers(PageRequest pageRequest) {
        return userRepo.findAll(pageRequest);
    }

    @Override
    public User deleteUser(int userID, String auToken) throws Exception {
        String token = auToken.substring(7);
        User user = userRepo.findByUserID(userID).orElseThrow();
        user.setActive(0);
        return userRepo.save(user);
    }

    @Override
    public String resetPassword(String email, PasswordDTO passwordDTO) throws DataNotFound {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new DataNotFound(language.getLocale("data.not.found")));
        if(passwordDTO.getPassword().equalsIgnoreCase(passwordDTO.getRe_password()) == false){
            return null;
        }
        user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
        userRepo.save(user);
        return "Cập nhật thành công";
    }
}
