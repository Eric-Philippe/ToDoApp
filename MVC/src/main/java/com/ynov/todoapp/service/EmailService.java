package com.ynov.todoapp.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetEmail(String to, String token) {
        String subject = "Password Reset Request";
        String resetUrl = "http://localhost:8080/reset-password?token=" + token;
        String message = "To reset your password, click the link below:"
                + resetUrl;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setText(message);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setFrom("noreply@todoapp.com");

        mailSender.send(mail);
    }
}
