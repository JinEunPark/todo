package com.nothing.todo_app.service;

import com.nothing.todo_app.model.ProfileEntity;
import com.nothing.todo_app.persistence.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
//profile Entity class
/*
mothod discription
createProfile ProfileEntity -> save and return again
retrieveProfile ProfileEntity -> retrieve profile enetity By user Id
deleteProfile ProfileEntity -> delete user's profile and return null because relation of profile is one by one so...
validate -> validate profile Entity


*/
public class ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    //testing make user profile only with status message;
    public String testProfile(ProfileEntity profileEntity) {

        profileEntity = ProfileEntity.builder().statusMessage("new profile").phoneNumber("000000000").build();
        profileRepository.save(profileEntity);
        ProfileEntity savedProfileEntity = profileRepository.findById(profileEntity.getProfileId()).get();

        return savedProfileEntity.getStatusMessage();
    }



    //save profile Entity to persistence and return it again
    public List<ProfileEntity> createProfile(final ProfileEntity profileEntity){
        validateProfileEntity(profileEntity);

        profileRepository.save(profileEntity);
        log.info("profile Id:{} is saved",profileEntity.getProfileId());

        return profileRepository.findByUserId(profileEntity.getUserId());
    }



    //get user's profile for show to the controller
    public List<ProfileEntity> retrieveProfileEntity(final String userId){
        return profileRepository.findByUserId(userId);
    }

    //delete profileEntity
    public List<ProfileEntity> deleteProfile(final ProfileEntity profileEntity){

        try{
            profileRepository.delete(profileEntity);
        }catch (Exception e){
            log.error("error deleting profile");
            throw new RuntimeException("error deleting profile"+profileEntity.getProfileId());
        }

        return retrieveProfileEntity(profileEntity.getUserId());//one to one 관계니까 null 값이 들어갈거임.
    }




    public List<ProfileEntity> updateProfileEntity(final ProfileEntity profileEntity){
        validateProfileEntity(profileEntity);

        final Optional<ProfileEntity> originalProfileEntity = profileRepository.findById(profileEntity.getProfileId());

        originalProfileEntity.ifPresent(profile->{
            profile.setName(profileEntity.getName());
            profile.setUserId(profileEntity.getUserId());
            profile.setStatusMessage(profileEntity.getStatusMessage());
            profile.setPhoneNumber(profileEntity.getPhoneNumber());
            profile.setUserEmail(profileEntity.getUserEmail());
            profile.setUserPhoto(profileEntity.getUserPhoto());
            profileRepository.save(profile);
        });
        return retrieveProfileEntity(profileEntity.getUserId());
    }



    //validate profile Entity's user Id and if it is Empty
    private void validateProfileEntity(final ProfileEntity profileEntity){
        if(profileEntity == null){
            log.warn("profile entity cannot be null");
            throw new RuntimeException("profileEntity cannot be null");
        }
        if(profileEntity.getUserId() == null){
            log.warn("unknown user");
            throw new RuntimeException("Unknown user");
        }
    }



}
