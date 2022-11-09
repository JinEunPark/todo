package com.nothing.todo_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@Slf4j
@Entity
public class Profile extends BaseEntity{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")//문자열로 아이디 생성함.
    String profileId;

    @OneToOne
    UserEntity userEntity;
    String statusMessage;
    String phoneNumber;
    String name;
    String userPhoto;

}
