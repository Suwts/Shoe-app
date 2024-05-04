package com.shoe.service;

import com.shoe.dto.ReqRes;
import org.springframework.stereotype.Service;

public interface AuthService {
    public ReqRes signUp(ReqRes register);
    public ReqRes signIn(ReqRes login);
    public ReqRes refreshToken(ReqRes refresh);
    public boolean existsByEmail(String email);
}
