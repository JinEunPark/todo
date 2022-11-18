package com.nothing.todo_app.persistence;

import com.nothing.todo_app.model.ProfilePictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfilePictureRepository extends JpaRepository<ProfilePictureEntity,String> {

    List<ProfilePictureEntity> findByUserId(String userId);
}
