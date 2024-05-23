package com.shoe.service;

import com.shoe.exception.DataNotFound;
import jakarta.mail.MessagingException;

public interface EmailService {
    String forgotPassword(String email) throws DataNotFound, MessagingException;
}
