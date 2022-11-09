package com.nothing.todo_app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Todo")
public class TodoEntity extends BaseEntity{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")//문자열로 아이디 생성함.
    private String id;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)//fetch type lazy로 비동기적인 실행함.
    @JoinColumn(name="user_id")
    private String userId;
    private String title;
    private boolean done;


}
