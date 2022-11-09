package com.nothing.todo_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ResponseDTO<T> {
    private List<T> data;
    private String error;
}
