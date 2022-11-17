package com.nothing.todo_app.service;

import com.nothing.todo_app.model.TodoEntity;
import com.nothing.todo_app.model.UserEntity;
import com.nothing.todo_app.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*UserService
 * 유저 데이터베이스에 저장된 유저를 가져올 때 사용
 * UserRepository를 이용해 사용자 생성, 로그인 인증 메서드 작성
 * */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private void validate(final UserEntity userEntity) {
        if (userEntity == null) {
            log.warn("entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }
        if (userEntity.getUsername() == null) {
            log.warn("unknown user");
            throw new RuntimeException("Unknown user");
        }
    }

    public UserEntity create(final UserEntity userEntity) {
        if(userEntity == null || userEntity.getUsername() == null ) {
            throw new RuntimeException("Invalid arguments");
        }
        final String username = userEntity.getUsername();
        if(userRepository.existsByUsername(username)) {
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }

        return userRepository.save(userEntity);
    }
    public UserEntity retrieve(final String userName) {//user
        return userRepository.findByUsername(userName);
    }
    public void update(final UserEntity entity) {
        // (1) 저장 할 엔티티가 유효한지 확인한다.
        validate(entity);

        // (2) 넘겨받은 엔티티 id를 이용해 UserEntity를 가져온다. 존재하지 않는 엔티티는 업데이트 할 수 없기 때문이다.
        final Optional<UserEntity> original = userRepository.findById(entity.getId());

        original.ifPresent(user -> {
            // (3) 반환된 UserEntity가 존재하면 값을 새 entity의 값으로 덮어 씌운다.
            user.setPassword(entity.getPassword());
            user.setRole(entity.getRole());
            user.setAuthProvider(entity.getAuthProvider());
            // (4) 데이터베이스에 새 값을 저장한다.
            userRepository.save(user);
        });
    }

    public UserEntity getByCredentials(final String username, final String password, final PasswordEncoder encoder) {
        final UserEntity originalUser = userRepository.findByUsername(username);

        // matches 메서드를 이용해 패스워드가 같은지 확인
        if(originalUser != null &&
                encoder.matches(password,
                        originalUser.getPassword())) {
            return originalUser;
        }
        return null;
    }

}
