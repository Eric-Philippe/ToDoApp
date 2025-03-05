package com.ynov.todoapp.service;

import com.ynov.todoapp.model.User;
import com.ynov.todoapp.repository.UserRepository;
import com.ynov.todoapp.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public void register(User user) {
        userRepository.save(user);
    }

    public Optional<User> authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByEmail(username);
        }

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return userOpt;
        }
        return Optional.empty();
    }

    public boolean sendPasswordResetLink(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = TokenGenerator.generateToken();
            user.setToken(token);

            LocalDateTime expiry = LocalDateTime.now().plusHours(1);
            user.setResetTokenExpiry(expiry);
            userRepository.save(user);

            emailService.sendResetEmail(user.getEmail(), token);

            return true;
        }

        return false;
    }

    public boolean resetPassword(String token, String password) {
        System.out.println(token);
        Optional<User> userOpt = userRepository.findByToken(token);

        System.out.println(userOpt);

        if (userOpt.isPresent() && userOpt.get().getResetTokenExpiry().isAfter(LocalDateTime.now())) {
            User user = userOpt.get();
            user.setPassword(password);
            user.setToken(null);
            user.setResetTokenExpiry(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
