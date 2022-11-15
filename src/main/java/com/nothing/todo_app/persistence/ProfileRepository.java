package com.nothing.todo_app.persistence;

import com.nothing.todo_app.model.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<ProfileEntity, String> {
    List<ProfileEntity> findByUserId(String userId);


}
