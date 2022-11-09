package com.nothing.todo_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Slf4j
@Table
public class UserEntity extends BaseEntity{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")
    String id;
    String userName;
    String userPhoneNumber;
    @Column(name="user_id")
    String userId;
    String userPassword;

    @OneToMany(mappedBy = "userId")
    List<TodoEntity> userTodoList = new ArrayList<TodoEntity>();

}
