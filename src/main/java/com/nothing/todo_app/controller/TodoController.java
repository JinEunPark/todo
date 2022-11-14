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
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo(){
        String str = todoService.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDto todoDto){

        try{
            String temporyUserId = "tempory-user";

            TodoEntity todoEntity = TodoDto.toEntity(todoDto);
            todoEntity.setId(null);

            todoEntity.setUserId(temporyUserId);

            List<TodoEntity> todoEntities = todoService.create(todoEntity);
            List<TodoDto> todoDtos = todoEntities.stream().map(TodoDto::new).collect(Collectors.toList());
            ResponseDTO<TodoDto> responseDTO = ResponseDTO.<TodoDto>builder().data(todoDtos).build();
            return ResponseEntity.ok().body(responseDTO);

        }catch (Exception e){

            String error = e.getMessage();
            ResponseDTO<TodoDto> responseDTO = ResponseDTO.<TodoDto>builder().error(error).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }
}
