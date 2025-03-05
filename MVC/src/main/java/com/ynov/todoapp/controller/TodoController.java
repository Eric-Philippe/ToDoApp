package com.ynov.todoapp.controller;

import com.ynov.todoapp.model.User;
import com.ynov.todoapp.service.TodoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/todos")
    public String showTodos(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("todos", todoService.getTodos(user));
            model.addAttribute("username", user.getUsername());
        } else {
            return "redirect:/login";
        }
        return "todos";
    }

    @PostMapping("/todos")
    public String createTodo(HttpSession session, @RequestParam String task) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            todoService.createTodo(user, task);
            return "redirect:/todos";
        }
        return "redirect:/login";
    }

    @PostMapping("/todos/{id}/toggle")
    public String toggleTodo(@PathVariable Long id) {
        todoService.toggleTodoCompletion(id);
        return "redirect:/todos";
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return "redirect:/todos";
    }

    @PostMapping("/todos/{id}/update")
    public String updateTodo(@PathVariable Long id, @RequestParam String task) {
        todoService.updateTodo(id, task);
        return "redirect:/todos";
    }
}
