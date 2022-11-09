package com.nothing.todo_app.persistence;

import com.nothing.todo_app.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity,String> {
    List<TodoEntity> findByUserId(String userId);

}
