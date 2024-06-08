package org.example.chatroom.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    private String content;
    private int userId;
    private int groupId;
}
