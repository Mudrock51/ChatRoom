package org.example.chatroom.controller;

import org.example.chatroom.entity.Message;
import org.example.chatroom.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MessageController {

    @Autowired
    private MessageService messageService;

    // 获取群组聊天历史记录
    @GetMapping("/{userId}/{groupId}/message")
    public ResponseEntity<List<Message>> getGroupMessages(@PathVariable String groupId) {
        List<Message> messages = messageService.getMessagesByGroupId(Integer.parseInt((groupId)));
        return ResponseEntity.ok(messages);
    }

    // 发送群组信息、本地存储
    @PostMapping("/{userId}/{groupId}/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Map<String, String> request){

        Message message = new Message();
        message.setMessageContent(request.get("content"));
        message.setUserId(Integer.parseInt(request.get("userId")));
        message.setGroupId(Integer.parseInt(request.get("groupId")));

        System.out.println("Debug GroupId:" + message.getGroupId());
        System.out.println("Debug UserId:" + message.getUserId());
        System.out.println("Debug MessageContent:" + message.getMessageContent());

        messageService.saveMessage(message);

        return ResponseEntity.ok(message);
    }

    // 新增方法：获取所有消息
    @GetMapping("/all")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    // 新增方法：删除消息
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int messageId) {
        boolean success = messageService.deleteMessageById(messageId);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(500).build();
        }
    }
}
