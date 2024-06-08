package org.example.chatroom.convert;

import org.example.chatroom.dto.MessageDTO;
import org.example.chatroom.entity.Message;

public class MessageConverter {

    public static MessageDTO convertUser(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setUserId(message.getUserId());
        messageDTO.setGroupId(message.getGroupId());
        return messageDTO;
    }

    public static Message convertUser(MessageDTO messageDTO) {
        Message message = new Message();
        message.setMessageContent(messageDTO.getContent());
        message.setUserId(messageDTO.getUserId());
        message.setGroupId(message.getGroupId());
        return message;
    }
}
