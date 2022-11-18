package com.nothing.todo_app;

import com.nothing.todo_app.properties.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//To enable the ConfigurationProperties feature, you need to add
//@EnableConfigurationProperties annotation to any configuration class.

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})

public class TodoAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
    }

}
