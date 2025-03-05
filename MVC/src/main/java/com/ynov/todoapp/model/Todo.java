package com.ynov.todoapp.model;

import jakarta.persistence.*;

@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String task;
    private String postedDate;
    // completed is a date because it will be set to the date when the task is completed and null if it is not completed
    private String completed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and setters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getTask() { return task; }
    public String getPostedDate() { return postedDate; }
    public String getCompleted() { return completed; }
    public boolean isCompleted() { return completed != null; }
    public User getUser() { return user; }

    public void setTitle(String title) { this.title = title; }
    public void setTask(String task) { this.task = task; }
    public void setPostedDate(String postedDate) { this.postedDate = postedDate; }
    public void setCompleted(String completed) { this.completed = completed; }
    public void setUser(User user) { this.user = user; }
}
