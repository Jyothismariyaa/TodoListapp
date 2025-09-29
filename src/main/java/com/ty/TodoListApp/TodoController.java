package com.ty.TodoListApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todos")
public class TodoController {
	@Autowired
    private TodoRepository todoRepository; // KEPT: Repository field name is unchanged

    // GET: Show all todo items and the form to add a new one
    @GetMapping
    public String listTodos(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        model.addAttribute("newTodo", new TodoItem()); // CHANGED: Uses TodoItem
        return "todo-list"; // Corresponds to todo-list.html
    }

    // POST: Add a new todo item
    @PostMapping
    public String addTodo(@ModelAttribute("newTodo") TodoItem newTodo) { // CHANGED: Parameter type is TodoItem
        newTodo.setCompleted(false);
        todoRepository.save(newTodo);
        return "redirect:/todos";
    }

    // GET: Toggle todo item completion status
    @GetMapping("/toggle/{id}")
    public String toggleTodoCompletion(@PathVariable Long id) {
        // CHANGED: Local variable type is now TodoItem
        // NOTE: The repository must be configured to return TodoItem objects
        TodoItem todo = (TodoItem) todoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid todo item Id:" + id)); 
            
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);
        return "redirect:/todos";
    }

    // GET: Delete a todo item
    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return "redirect:/todos";
    }
}