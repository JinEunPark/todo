package com.nothing.todo_app.controller;

import com.nothing.todo_app.dto.ResponseDTO;
import jdk.incubator.vector.VectorOperators;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
@ResponseBody
public class TestController {


    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerResponseEntity(){
        List<String> list = new ArrayList<>();
        list.add("hello world ! I'm ResponseEntity and you got 400!");
        ResponseDTO<String> responseDTO = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.badRequest().body(responseDTO);
    }


//    @GetMapping("/testReqduestBody")
//    public ResponseDTO<String> testControllerResponseBody(){
//        List<String> list = new ArrayList<>();
//        list.add("helloWorld ! I'm ResponseBody");
//        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
//        return response;
//    }

//    @GetMapping("/testRequestBody")
//    public String testControllerRequestBody(@RequestBody TestRequestBodyDto testRequestBodyDto){
//        return "hello world Id :"+ testRequestBodyDto.getMessage()+"\nmessage:"+testRequestBodyDto.getId();
//    }

//    @GetMapping("/getMapping")
//    public String testController(){
//        return "hello word";
//    }
//    @GetMapping("/{id}")
//    public String testControllerWithPathVariables(@PathVariable(required = false) int id){
//        return id+"hello world!";
//    }
}
