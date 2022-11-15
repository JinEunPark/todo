package com.nothing.todo_app.service;

import com.nothing.todo_app.dto.TodoDto;
import com.nothing.todo_app.model.TodoEntity;
import com.nothing.todo_app.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        validate(todoEntity);

        todoRepository.save(todoEntity);
        log.info("entity Id:{} is saved",todoEntity.getId());
        return todoRepository.findByUserId(todoEntity.getUserId());
        //findByUserId() 이니까 userId로 받을 것
    }
    //get user's todoEntity for as a list
    public List<TodoEntity> retrieve(final String userId){//user 의 todo 목록을 반환함.
        return todoRepository.findByUserId(userId);
    }




    //for the update dotoEntity in the todo repository
    public List<TodoEntity> update(final TodoEntity todoEntity){

        validate(todoEntity);

        final Optional<TodoEntity> original = todoRepository.findById(todoEntity.getId());

        original.ifPresent(todo ->{//original 이 <TodoEntity>이라서 람다식으로 표현이 가능함.
            todo.setTitle(todoEntity.getTitle());
            todo.setDone(todoEntity.isDone());
            todoRepository.save(todo);
        });

        return retrieve(todoEntity.getUserId());
    }
    //for delete todoEntity
    public List<TodoEntity> delete(final TodoEntity todoEntity){
        try{
            todoRepository.delete(todoEntity);

        }catch(Exception e){
            log.error("error deleting entity ",todoEntity.getId(),e);
            throw new RuntimeException("error deleting entity"+todoEntity.getId());
        }
        return retrieve(todoEntity.getUserId());
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
