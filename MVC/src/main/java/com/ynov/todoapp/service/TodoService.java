package com.ynov.todoapp.service;

import com.ynov.todoapp.model.Todo;
import com.ynov.todoapp.model.User;
import com.ynov.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getTodos(User user) {
        return todoRepository.findByUserId(user.getId());
    }

    public Todo createTodo(User user, String task) {
        Todo todo = new Todo();
        todo.setTask(task);
        todo.setCompleted(null);
        todo.setUser(user);
        return todoRepository.save(todo);
    }

    public Todo toggleTodoCompletion(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow();
        todo.setCompleted(!todo.isCompleted() ? new Date().toString() : null);
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public Todo updateTodo(Long id, String task) {
        Todo todo = todoRepository.findById(id).orElseThrow();
        todo.setTask(task);
        return todoRepository.save(todo);
    }
}
