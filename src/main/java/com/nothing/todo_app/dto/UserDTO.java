package com.nothing.todo_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*UserDTO
 * UserController에 사용될 DTO*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String token;
    private String username;
    private String password;
    private String id;
}
