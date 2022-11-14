package com.nothing.todo_app.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value={AuditingEntityListener.class})
@MappedSuperclass
@Data
public abstract class BaseTimeEntity {
    @CreatedDate
    @Column
    private LocalDateTime regTime;//등록시간

    @LastModifiedDate
    private LocalDateTime updateTime;//수정시간
}
