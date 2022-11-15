package com.nothing.todo_app.controller;

import com.nothing.todo_app.dto.ResponseDTO;
import com.nothing.todo_app.dto.TodoDto;
import com.nothing.todo_app.model.TodoEntity;
import com.nothing.todo_app.service.TodoService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
/*
주석 달기 너무 싫어용
 */
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String temporaryUserId = "temporary-user";//임의의 아이디 생성
        List<TodoEntity> entities = todoService.retrieve(temporaryUserId);
        List<TodoDto> todoDtoList = entities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDTO<TodoDto> responseDTO = ResponseDTO.<TodoDto>builder().data(todoDtoList).build();
        return ResponseEntity.ok().body(responseDTO);
    }


    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDto dto) {

        String temporaryUserId = "temporary-user";
        ;
        TodoEntity todoEntity = TodoDto.toEntity(dto);

        todoEntity.setUserId(temporaryUserId);
        List<TodoEntity> todoEntities = todoService.update(todoEntity);

        List<TodoDto> todoDtos = todoEntities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDTO<TodoDto> responseDTO = ResponseDTO.<TodoDto>builder().data(todoDtos).build();

        return ResponseEntity.ok().body(responseDTO);
    }


    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDto todoDto) {//생성하기 위해서 사용한 postMapping

        try {
            String temporaryUserId = "temporary-user";//

            TodoEntity todoEntity = TodoDto.toEntity(todoDto);
            todoEntity.setId(null);

            todoEntity.setUserId(temporaryUserId);

            List<TodoEntity> todoEntities = todoService.create(todoEntity);

            List<TodoDto> todoDtos = todoEntities.stream().map(TodoDto::new).collect(Collectors.toList());
            ResponseDTO<TodoDto> responseDTO = ResponseDTO.<TodoDto>builder().data(todoDtos).build();

            return ResponseEntity.ok().body(responseDTO);

        } catch (Exception e) {

            String error = e.getMessage();
            ResponseDTO<TodoDto> responseDTO = ResponseDTO.<TodoDto>builder().error(error).build();
            return ResponseEntity.badRequest().body(responseDTO);

        }

    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDto todoDto) {
        try {
            String temporaryUserId = "temporary-user";//
            TodoEntity todoEntity = TodoDto.toEntity(todoDto);
            todoEntity.setUserId(temporaryUserId);
            List<TodoEntity> entities = todoService.delete(todoEntity);
            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

            ResponseDTO<TodoDto> responseDTO = ResponseDTO.<TodoDto>builder().data(dtos).build();
            return ResponseEntity.ok().body(responseDTO);

        } catch (Exception e) {

            String error = e.getMessage();
            ResponseDTO<TodoDto> responseDTO = ResponseDTO.<TodoDto>builder().error(error).build();
            return ResponseEntity.badRequest().body(responseDTO);

        }

    }
}
