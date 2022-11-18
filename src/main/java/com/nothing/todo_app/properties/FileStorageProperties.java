package com.nothing.todo_app.properties;

//https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "file")
//@ConfigurationProperties(prefix = "...")는 어플리케이션이 시작될 때 application.yml또는 application.properties에
//        설정된 file.upload~로 시작하는 설정값을 가져옴..! getter와 setter를 통해 location이라는 변수에 우리가 설정한 값이 지정된다.

public class FileStorageProperties {
    private String uploadDir;//여기에 properties 에 저장한 경로가 입력되는거임

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
