package com.shoe.service.impl;

import com.shoe.Utils.JwtsUtils;
import com.shoe.Utils.Language;
import com.shoe.dto.ReqRes;
import com.shoe.entity.Role;
import com.shoe.entity.User;
import com.shoe.exception.DataNotFound;
import com.shoe.reponsitory.RoleRepo;
import com.shoe.reponsitory.UserRepo;
import com.shoe.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthServiceImpl implements AuthService {
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
    public ReqRes signUp(ReqRes register) {
        ReqRes reqRes = new ReqRes();
        try{
            User user = new User();
            user.setEmail(register.getEmail());
            user.setUserNames(register.getUserName());
            user.setPassword(passwordEncoder.encode(register.getPassword()));
            user.setSex(register.getSex());
            user.setFullName(register.getFullName());
            user.setActive(register.getActive());
            user.setRoleid(register.getRole_id());
            Role role = roleRepo.findByRoleID(register.getRole_id()).orElseThrow(() -> new DataNotFound(language.getLocale("data.not.found")));
            user.setRole(role);
            if(register.getPassword().equalsIgnoreCase(register.getRe_password()) && existsByEmail(register.getEmail()) == false){
                User u = userRepo.save(user);
                if(u != null && u.getUserID() > 0){
                    reqRes.setUser(u);
                    reqRes.setMessage(language.getLocale("user.register.successful"));
                    reqRes.setStatusCode(200);
                }
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setError(e.getMessage());
        }
        return reqRes;
    }

    @Override
    public ReqRes signIn(ReqRes login) {
        ReqRes reqRes = new ReqRes();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
            var user = userRepo.findByEmail(login.getEmail()).orElseThrow();
            var jwt = jwtsUtils.generateToken(user);
            var refreshToken = jwtsUtils.generateRefeshToken(new HashMap<>(), user);
            reqRes.setStatusCode(200);
            reqRes.setToken(jwt);
            reqRes.setRefreshToken(refreshToken);
            reqRes.setMessage(language.getLocale("user.login.successful"));
        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setError(e.getMessage());
        }
        return reqRes;
    }

    @Override
    public ReqRes refreshToken(ReqRes refresh) {
        ReqRes reqRes = new ReqRes();
        String email = jwtsUtils.extractUserName(refresh.getRefreshToken());
        User user = userRepo.findByEmail(refresh.getEmail()).orElseThrow();
        if(jwtsUtils.isTokenValid(refresh.getToken(), user)){
            var jwt = jwtsUtils.generateToken(user);
            reqRes.setStatusCode(200);
            reqRes.setToken(jwt);
            reqRes.setRefreshToken(refresh.getToken());
            reqRes.setMessage("Login successfully");
        }
        reqRes.setStatusCode(500);
        return reqRes;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }
}
