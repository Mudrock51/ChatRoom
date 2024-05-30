package org.example.chatroom.controller;

import org.example.chatroom.entity.Message;
import org.example.chatroom.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody Message message) {
        message.setMessageTime(LocalDateTime.now());
        messageService.save(message);
        return "Message sent and saved to database.";
    }


    @GetMapping("/receive/{id}")
    public Message receiveMessage(@PathVariable Long id) {
        return messageService.getById(id);
    }

    // 获取某个聊天组的历史消息
    @GetMapping("/{groupId}")
    public List<Message> getMessagesByGroupId(@PathVariable Long groupId) {
        return messageService.getMessagesByGroupId(groupId);
    }
}

