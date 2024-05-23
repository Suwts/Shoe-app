package com.shoe.service;

import com.shoe.dto.PasswordDTO;
import com.shoe.dto.UserDTO;
import com.shoe.entity.User;
import com.shoe.exception.DataNotFound;
import com.shoe.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {
    public UserDTO signUp(UserDTO register);
    public UserDTO signIn(UserDTO login);
    public UserDTO refreshToken(UserDTO refresh);
    public boolean existsByEmail(String email);
    User getDetail(String token) throws Exception;
    User updateUser(int userId, UserDTO userDTO,String auToken) throws Exception;
    Page<User> getUsers(PageRequest pageRequest);
    User deleteUser(int userID, String auToken) throws Exception;
    String resetPassword(String email, PasswordDTO passwordDTO) throws DataNotFound;
}
