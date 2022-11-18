package com.nothing.todo_app.exception;
//파일 저장할 때 오류가 발생하면 Exception 을 발생시키는 객체
public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

