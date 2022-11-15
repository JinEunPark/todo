package com.nothing.todo_app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table
@Slf4j
@Entity
//사용자 프로파일 entity 회워가입 할때 이 정보 받을 것
public class ProfileEntity extends BaseEntity{

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")//문자열로 아이디 생성함.
    String profileId;
    String userId;
    String name;
    String statusMessage;
    String phoneNumber;
    String userPhoto;
    String userEmail;
}
