package com.shoe.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtsUtils {
    private SecretKey key;
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 100;  // milisecs
    public JwtsUtils(){
        String SecretKey = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";    //Tạo ra 1 key tự do
        byte[] keyByte = Base64.getDecoder().decode(SecretKey.getBytes(StandardCharsets.UTF_8));        //Mã hóa key
        this.key = new SecretKeySpec(keyByte, "HmacSHA256");
     }

     //Tạo mã thông báo token cho người dùng
     public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
     }

     //Refresh lại mã thông báo token cho người
     public String generateRefeshToken(HashMap<String, Object> claim, UserDetails userDetails){
        return Jwts.builder()
                .claims(claim)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
     }

     //Lấy ra tên người dùng
     public String extractUserName(String token){
        return extractClaims(token, Claims :: getSubject);
     }

    //Lấy thông tin được gửi về
    public <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
    }

    //Kiểm tra tính hợp lệ thông tin người dùng nhập và thông tin trả về
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()));
    }

    //Kiểm tra thông tin đã hết hạn chưa
    public boolean isExpired(String token){
        return extractClaims(token, Claims :: getExpiration).before(new Date());
    }
}
