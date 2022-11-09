package com.nothing.todo_app.dto;

import lombok.Data;


@Data
public class ChatMessage {
    public enum MessageType{//inviting message and normal message
        ENTER, TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
