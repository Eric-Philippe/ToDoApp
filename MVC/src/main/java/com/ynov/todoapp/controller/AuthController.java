package com.ynov.todoapp.controller;

import com.ynov.todoapp.model.User;
import com.ynov.todoapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        userService.register(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        Optional<User> userOpt = userService.authenticate(username, password);
        if (userOpt.isPresent()) {
            session.setAttribute("user", userOpt.get());
            return "redirect:/todos";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam String email, Model model) {
        boolean result = userService.sendPasswordResetLink(email);
        if (result) {
            model.addAttribute("message", "Password reset link sent to your email.");
        } else {
            model.addAttribute("error", "Email not found.");
        }

        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam String token, @RequestParam String password, Model model) {
        boolean result = userService.resetPassword(token, password);
        if (result) {
            model.addAttribute("message", "Password successfully reset.");
        } else {
            model.addAttribute("error", "Invalid or expired token.");
        }

        return "login";
    }
}