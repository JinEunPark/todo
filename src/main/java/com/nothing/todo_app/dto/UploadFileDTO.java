package com.nothing.todo_app.dto;

import com.nothing.todo_app.model.ProfilePictureEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//file upload 할 때 반환할 객체
public class UploadFileDTO {

    private String fileId;
    private String userId;//아직 보안 설정 안해서...
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public UploadFileDTO(ProfilePictureEntity profilePictureEntity) {
        this.userId = profilePictureEntity.getUserId();
        this.fileName = profilePictureEntity.getFileName();
        this.fileDownloadUri = profilePictureEntity.getFileDownloadUri();
        this.fileType = profilePictureEntity.getFileType();
        this.size = profilePictureEntity.getSize();
    }

    //constructor for the upload fileService
    public UploadFileDTO(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public static ModelMapper modelMapper = new ModelMapper();


    public static ProfilePictureEntity toProfilePictureEntity(UploadFileDTO uploadFileDTO){
        ProfilePictureEntity profilePictureEntity = modelMapper.map(uploadFileDTO, ProfilePictureEntity.class);
        return profilePictureEntity;
    }






// Getters and Setters (Omitted for brevity)
}
