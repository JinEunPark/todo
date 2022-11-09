package com.nothing.todo_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class GroupTodoEntity extends BaseEntity{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")//문자열로 아이디 생성함.
    String groupId;
    String title;
    @OneToMany
    List<UserEntity> userEntities= new ArrayList<>();

}
