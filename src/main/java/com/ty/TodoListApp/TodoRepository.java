package com.ty.TodoListApp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository <TodoItem, Long>{

}