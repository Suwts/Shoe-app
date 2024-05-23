package com.shoe.service.impl;

import com.shoe.Utils.Language;
import com.shoe.Utils.MailUtil;
import com.shoe.entity.User;
import com.shoe.exception.DataNotFound;
import com.shoe.reponsitory.UserRepo;
import com.shoe.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Language language;

    @Autowired
    private MailUtil mailUtil;

    @Override
    public String forgotPassword(String email) throws DataNotFound, MessagingException {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new DataNotFound(language.getLocale("data.not.found")));
        try{
            mailUtil.sendEmailForgotPassword(email);
        }catch (Exception e){
            throw new DataNotFound(language.getLocale("data.not.found"));
        }
        return "Kiểm tra email của bạn";
    }
}
