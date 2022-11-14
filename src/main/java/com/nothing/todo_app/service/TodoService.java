package com.nothing.todo_app.service;

import com.nothing.todo_app.model.TodoEntity;
import com.nothing.todo_app.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    //for the test json object
    public String testService(){
        TodoEntity todoEntity = TodoEntity.builder().title("first thing todo").build();
        todoRepository.save(todoEntity);

        TodoEntity savedEntity = todoRepository.findById(todoEntity.getId()).get();
        return savedEntity.getTitle();

    }
    //create List TodoEntity and return saved todo entity
    public List<TodoEntity> create(final TodoEntity todoEntity){
        validate(todoEntity );

        todoRepository.save(todoEntity);
        log.info("entity Id:{} is saved",todoEntity.getId());
        return todoRepository.findByUserId(todoEntity.getId());
    }

    //todo object validation
    private void validate(final TodoEntity todoEntity){
        if(todoEntity == null){
            log.warn("entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }
        if(todoEntity.getUserId()==null){
            log.warn("unknown user");
            throw new RuntimeException("Unknown user");
        }

    }
}
