package com.nothing.todo_app.dto;

import com.nothing.todo_app.model.ProfileEntity;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
//class for profileDto please check the annotation
public class ProfileDto {

    String profileId;
    String userId;
    String name;
    String statusMessage;
    String phoneNumber;
    String userPhoto;
    String userEmail;
    public static ModelMapper modelMapper = new ModelMapper();



    public ProfileDto(ProfileEntity profileEntity) {
        this.profileId = profileEntity.getProfileId();
        this.userId = profileEntity.getUserId();
        this.name = profileEntity.getName();
        this.statusMessage = profileEntity.getStatusMessage();
        this.phoneNumber = profileEntity.getPhoneNumber();
        this.userPhoto = profileEntity.getUserPhoto();
        this.userEmail = profileEntity.getUserEmail();
    }

    //ProfileDto -> ProfileEntity obj conversion
    public static ProfileEntity toProfileEntity(ProfileDto profileDto){
        return modelMapper.map(profileDto,ProfileEntity.class);
    }
}
