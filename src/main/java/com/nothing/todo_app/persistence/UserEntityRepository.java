package com.nothing.todo_app.persistence;

import com.nothing.todo_app.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {
    List<UserEntity> findUserEntityByUserId(String userId);
}
