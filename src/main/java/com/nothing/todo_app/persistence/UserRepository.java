package com.nothing.todo_app.persistence;

import com.nothing.todo_app.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* UserRepository
 * UserEntity를 사용하기 위한 interface
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{

    UserEntity findByUsername(String username);
    Boolean existsByUsername(String username);
    UserEntity findByUsernameAndPassword(String username, String password);
}
