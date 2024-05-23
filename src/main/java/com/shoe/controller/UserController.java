package com.shoe.controller;

import com.shoe.dto.PasswordDTO;
import com.shoe.dto.UserDTO;
import com.shoe.entity.Product;
import com.shoe.entity.User;
import com.shoe.exception.DataNotFound;
import com.shoe.response.ProductPageRespone;
import com.shoe.response.UserPage;
import com.shoe.response.UserResponse;
import com.shoe.response.UserUpdateRes;
import com.shoe.service.EmailService;
import com.shoe.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @PostMapping("/signin")
    public ResponseEntity<UserDTO> signIn(@RequestBody UserDTO signInRequest){
        return ResponseEntity.ok(userService.signIn(signInRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody UserDTO signUpRequest){
        return ResponseEntity.ok(userService.signUp(signUpRequest));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<UserDTO> refreshToken(@RequestBody UserDTO refresh){
        return ResponseEntity.ok(userService.refreshToken(refresh));
    }

    @GetMapping("/detail")
    public ResponseEntity<UserResponse> getDetail(@RequestHeader("Authorization") String token) throws Exception {
        String jwtToken = token.substring(7);
        User user = userService.getDetail(jwtToken);
        return ResponseEntity.ok(UserResponse.fromUser(user));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserUpdateRes> updateUser(@PathVariable int userId, @RequestBody UserDTO userDTO,
                                                       @RequestHeader("Authorization") String auToken) {
        try {
            User updateUser = userService.updateUser(userId, userDTO, auToken);
            return ResponseEntity.ok(UserUpdateRes.fromUser(updateUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<UserPage> getAll(@RequestParam int page, @RequestParam int limit){
        PageRequest pageRequest = PageRequest.of(page-1, limit, Sort.by("userID").ascending());
        Page<User> user = userService.getUsers(pageRequest);
        int totalPage = user.getTotalPages();
        List<User> userList = user.getContent();
        return ResponseEntity.ok(new UserPage(userList, totalPage));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> deleteUser(@RequestParam int userID,@RequestHeader("Authorization") String auToken) throws Exception {
        return ResponseEntity.ok(userService.deleteUser(userID, auToken));
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) throws DataNotFound, MessagingException {
        return ResponseEntity.ok(emailService.forgotPassword(email));
    }

    @PutMapping("/set-password")
    public ResponseEntity<String> setPassword(@RequestParam String email, @RequestBody PasswordDTO passwordDTO) throws DataNotFound {
        return ResponseEntity.ok(userService.resetPassword(email, passwordDTO));
    }


}
