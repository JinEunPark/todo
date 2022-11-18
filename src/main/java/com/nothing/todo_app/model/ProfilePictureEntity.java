package com.nothing.todo_app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class ProfilePictureEntity extends BaseEntity{
    @Id
    private String fileName;//service layer 에서 uuid 생성할거임.
    private String userId;//아직 보안 설정 안해서...
    private String fileDownloadUri;
    private String fileType;
    private long size;
}
