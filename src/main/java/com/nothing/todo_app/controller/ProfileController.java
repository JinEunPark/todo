package com.nothing.todo_app.controller;

import com.nothing.todo_app.dto.ProfileDto;
import com.nothing.todo_app.dto.ResponseDTO;
import com.nothing.todo_app.dto.TodoDto;
import com.nothing.todo_app.model.ProfileEntity;
import com.nothing.todo_app.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("profile")
@AllArgsConstructor
public class ProfileController {

    private final String temporaryUserId = "temporary-user";//임의의 아이디 생성

    @Autowired
    private final ProfileService profileService;
    //RequestBody 방식으로 받으려고 userId 이외의 다른 값은 null인 profileDto로 전달함.
    //test done
    @GetMapping
    public ResponseEntity<?> showUserProfile(@RequestBody ProfileDto profileDto){
        //다른 값은 null인 객체로 전달했기 때문에 DB에서 다시 꺼내서 반환하게 만듦
        List<ProfileEntity> profileEntities = profileService.retrieveProfileEntity(profileDto.getUserId());
        List<ProfileDto> profileDtos = profileEntities.stream().map(ProfileDto::new).collect(Collectors.toList());
        ResponseDTO<ProfileDto> responseDTO = ResponseDTO.<ProfileDto>builder().data(profileDtos).build();

        return ResponseEntity.ok().body(responseDTO);

    }


    //creating profile
    //test done

    @PostMapping
    public ResponseEntity<?> createProfile(@RequestBody ProfileDto profileDto){

        try{
            ProfileEntity profileEntity = ProfileDto.toProfileEntity(profileDto);

            profileEntity.setUserId(temporaryUserId);
            profileEntity.setProfileId(null);
            List<ProfileEntity> profileEntities = profileService.createProfile(profileEntity);
            List<ProfileDto> profileDtos = profileEntities.stream().map(ProfileDto::new).collect(Collectors.toList());

            ResponseDTO<ProfileDto> responseDTO = ResponseDTO.<ProfileDto>builder().data(profileDtos).build();

            return ResponseEntity.ok().body(responseDTO);

        }catch(Exception e){

            String error = e.getMessage();
            ResponseDTO<ProfileDto> responseDTO = ResponseDTO.<ProfileDto>builder().error(error).build();

            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

//test done
    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody ProfileDto profileDto){

        ProfileEntity profileEntity = ProfileDto.toProfileEntity(profileDto);
        profileEntity.setUserId(temporaryUserId);
        List<ProfileEntity> profileEntities = profileService.updateProfileEntity(profileEntity);

        List<ProfileDto> profileDtos = profileEntities.stream().map(ProfileDto::new).collect(Collectors.toList());
        ResponseDTO<ProfileDto> responseDTO = ResponseDTO.<ProfileDto>builder().data(profileDtos).build();

        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping
    //delete profile entity may be... todo we should add this at client
    //test done
    public ResponseEntity<?> deleteProfile(@RequestBody ProfileDto profileDto){

        try{

            ProfileEntity profileEntity = ProfileDto.toProfileEntity(profileDto);
            profileEntity.setUserId(temporaryUserId);
            List<ProfileEntity> entities = profileService.deleteProfile(profileEntity);//delete

            List<ProfileDto> profileDtos = entities.stream().map(ProfileDto::new).collect(Collectors.toList());
            ResponseDTO<ProfileDto> responseDTO = ResponseDTO.<ProfileDto>builder().data(profileDtos).build();

            return ResponseEntity.ok().body(responseDTO);
            //rest object will be returned but this should be null so don't be afraid ass hold

        }catch(Exception e){
            String error = e.getMessage();
            ResponseDTO<ProfileDto> responseDTO = ResponseDTO.<ProfileDto>builder().error(error).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

}
