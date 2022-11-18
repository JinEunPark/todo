package com.nothing.todo_app.service;

import com.nothing.todo_app.dto.UploadFileDTO;
import com.nothing.todo_app.model.ProfileEntity;
import com.nothing.todo_app.model.ProfilePictureEntity;
import com.nothing.todo_app.persistence.ProfilePictureRepository;
import com.nothing.todo_app.persistence.ProfilePictureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ProfilePictureService {

    @Autowired
    ProfilePictureRepository profilePictureRepository;


    //save profilePicture Entity to persistence and return it again
    public List<ProfilePictureEntity> create(final ProfilePictureEntity profilePictureEntity){
        validateProfilePictureEntity(profilePictureEntity);

        profilePictureRepository.save(profilePictureEntity);
        log.info("profile Picture Id:{} is saved",profilePictureEntity.getFileName());

        return profilePictureRepository.findByUserId(profilePictureEntity.getUserId());
    }



    //get user's profilePicture for show to the controller
    public List<ProfilePictureEntity> retrieve(final String userId){
        return profilePictureRepository.findByUserId(userId);
    }

    //delete ProfilePictureEntity
    public List<ProfilePictureEntity> deleteProfile(final ProfilePictureEntity profilePictureEntity){

        try{
            profilePictureRepository.delete(profilePictureEntity);
        }catch (Exception e){
            log.error("error deleting profile");
            throw new RuntimeException("error deleting profile"+profilePictureEntity.getUserId());
        }

        return retrieve(profilePictureEntity.getUserId());//one to one 관계니까 null 값이 들어갈거임.
    }



    //update ProfilePictureEntity
    public List<ProfilePictureEntity> update(final ProfilePictureEntity profilePictureEntity){
        validateProfilePictureEntity(profilePictureEntity);

        final Optional<ProfilePictureEntity> originalProfilePictureEntity = profilePictureRepository.findById(profilePictureEntity.getFileName());

        originalProfilePictureEntity.ifPresent(profilePicture->{
            profilePicture.setFileName(profilePictureEntity.getFileName());
            profilePicture.setUserId(profilePictureEntity.getUserId());
            profilePicture.setFileDownloadUri(profilePictureEntity.getFileDownloadUri());
            profilePicture.setSize(profilePictureEntity.getSize());
            profilePictureRepository.save(profilePicture);
        });
        return retrieve(profilePictureEntity.getUserId());
    }



    //validate profile PictureEntity's user Id and if it is Empty
    private void validateProfilePictureEntity(final ProfilePictureEntity profilePictureEntity){
        if(profilePictureEntity == null){
            log.warn("profile entity cannot be null");
            throw new RuntimeException("profilePictureEntity cannot be null");
        }
        if(profilePictureEntity.getUserId() == null){
            log.warn("unknown user");
            throw new RuntimeException("Unknown user");
        }
    }




}
