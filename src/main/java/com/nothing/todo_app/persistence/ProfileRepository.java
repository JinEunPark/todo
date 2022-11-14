package com.nothing.todo_app.persistence;

import com.nothing.todo_app.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, String> {
    List<Profile> findByUserId(String userId);


}
