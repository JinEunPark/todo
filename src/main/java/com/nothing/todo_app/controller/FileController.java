package com.nothing.todo_app.controller;

import com.nothing.todo_app.dto.ResponseDTO;
import com.nothing.todo_app.dto.UploadFileDTO;
import com.nothing.todo_app.model.ProfilePictureEntity;
import com.nothing.todo_app.service.FileStorageService;
import com.nothing.todo_app.service.ProfilePictureService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//사용자 프로필 사진 넣는거 사용하기 위해서 사용한 클래스
@RestController
@Slf4j
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    //use for logging save file and upload todo if you wanna see more details please click "command+enter"
    //using two service for save binary and file entity
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ProfilePictureService profilePictureService;

    private final String temporaryUserId = "temporary-user";//임의의 아이디 생성

    @PostMapping("/uploadFile")
    public UploadFileDTO uploadFile(@RequestParam("file") MultipartFile file) {//upload file param

        String fileName = fileStorageService.storeFile(file);//file save using file service

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()//프로젝트명
                .path("/downloadFile/")//파일을 다운로드 하기 위한 경로 명시하기 위해서
                .path(fileName)//파일 이름
                .toUriString();

        //파일을 서버에 저장한 후에 builder 를 사용해서 파일에 대응되는 upload file 객체를 생성성함.
        UploadFileDTO uploadFileDTO = UploadFileDTO.builder()
                .fileName(fileName)
                .fileType(file.getContentType())
                .fileDownloadUri(fileDownloadUri)
                .size(file.getSize())
                .userId(temporaryUserId).build();
        //객체전환
        ProfilePictureEntity profilePictureEntity = UploadFileDTO.toProfilePictureEntity(uploadFileDTO);
        //저장된 객체를 DB에서 다시 꺼내서 뿌림.
        List<ProfilePictureEntity> profilePictureEntities = profilePictureService.create(profilePictureEntity);
        List<UploadFileDTO> uploadFileDTOS = profilePictureEntities.stream().map(UploadFileDTO::new).collect(Collectors.toList());

        return  uploadFileDTOS.get(0);

    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileDTO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{userId:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String userId, HttpServletRequest request) {
        List<ProfilePictureEntity> profilePictureEntities = profilePictureService.retrieve(userId);
        ProfilePictureEntity profilePictureEntity = profilePictureEntities.get(0);
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(profilePictureEntity.getFileName());

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}

