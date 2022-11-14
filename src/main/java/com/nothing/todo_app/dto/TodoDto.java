package com.nothing.todo_app.dto;

import com.nothing.todo_app.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data//이 어노테이션을 지정해야 serialize 가능하다.

public class TodoDto {

    private String id;
    private String title;
    private boolean done;

    public TodoDto(final TodoEntity entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    public static ModelMapper modelMapper = new ModelMapper();

    public static TodoEntity toEntity(TodoDto todoDto){
        TodoEntity todoEntity = null;
        todoEntity = modelMapper.map(todoDto,TodoEntity.class);
        return todoEntity;
    }


}
