package com.ynov.todoapp.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String token;
    private LocalDateTime resetTokenExpiry;

    @OneToMany(mappedBy = "user")
    private List<Todo> todos;

    // Getters and setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public List<Todo> getTodos() { return todos; }
    public String getToken() { return token; }
    public LocalDateTime getResetTokenExpiry() { return resetTokenExpiry; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setTodos(List<Todo> todos) { this.todos = todos; }
    public void setToken(String token) { this.token = token; }
    public void setResetTokenExpiry(LocalDateTime resetTokenExpiry) { this.resetTokenExpiry = resetTokenExpiry; }
}
