package com.shoe.Utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmailForgotPassword(String email) throws MessagingException {
        MimeMessage minMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(minMessage, true);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Set password");
        mimeMessageHelper.setText("""
                <div>
                    <a href="http://localhost:4200/forgot-password?email=%s" target="_blank">Nhấn vào đây để thay đổi mật khẩu</a>
                </div>
                """.formatted(email), true);
        javaMailSender.send(minMessage);

    }

}
