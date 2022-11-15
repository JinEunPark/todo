package com.nothing.todo_app.service;

import com.nothing.todo_app.model.UserEntity;
import com.nothing.todo_app.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*UserService
* 유저 데이터베이스에 저장된 유저를 가져올 때 사용
* UserRepository를 이용해 사용자 생성, 로그인 인증 메서드 작성
* */
@Slf4j
@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity){
        if(userEntity == null || userEntity.getUsername() == null){
            throw new RuntimeException("Invalid arguments");
        }
        final String username = userEntity.getUsername();
        if(userRepository.existsByUsername(username)){
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }

        return userRepository.save(userEntity);
    }

    public UserEntity getByCredentials(final String username, final String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
