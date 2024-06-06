package org.example.chatroom.controller;

import org.example.chatroom.dto.MessageDTO;
import org.example.chatroom.entity.Message;
import org.example.chatroom.entity.MessageType;
import org.example.chatroom.service.MessageService;
import org.example.chatroom.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat/{userId}")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private WebSocketService webSocketService;

    @GetMapping("/{groupId}/message")
    public ResponseEntity<List<Message>> getGroupMessages(@PathVariable String groupId) {
        List<Message> messages = messageService.getMessagesByGroupId(Integer.parseInt((groupId)));
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Map<String, String> request){

        Message message = new Message();
        message.setMessageContent(request.get("content"));
        message.setUserId(Integer.valueOf(request.get("userId")));
        message.setGroupId(Integer.valueOf(request.get("groupId")));

        System.out.println("Debug GroupId:" + message.getGroupId());
        System.out.println("Debug UserId:" + message.getUserId());
        System.out.println("Debug MessageContent:" + message.getMessageContent());

        messageService.saveMessage(message);

        // 消息转发
//        webSocketService.onMessage(
//                message.getGroupId(),
//                message.getUserId(),
//                "{ \"message\":\"" + message.getMessageContent() + "\" }"
//        );

        return ResponseEntity.ok(message);
    }
}
