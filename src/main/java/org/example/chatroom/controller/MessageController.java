package org.example.chatroom.controller;

/*
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.example.chatroom.entity.Message;
import org.example.chatroom.service.ChatMessageService;
import org.example.chatroom.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@ServerEndpoint("/api/chat/{groupId}/{userId}")
@Component
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatMessageService chatMessageService;


    //将收到的消息存入了Redis中
    @PostMapping("/send")
    public String sendMessage(@RequestBody Message message) throws IOException {
        message.setMessageTime(LocalDateTime.now());
        chatMessageService.addMessage(message.getChatGroupId(), message);
        //Trigger WebSocket message forwarding
        return "Message sent and cached to Redis.";
    }

    @OnMessage
    public void onMessage(@PathParam("groupId") Integer groupId,@PathParam("userId") Integer userId,String msg) throws IOException {
        messageService.handleMessage(groupId,userId,msg);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("groupId") Integer groupId, @PathParam("userId") Integer userId) throws IOException {
        messageService.addMemberToGroup(session, groupId, userId);
    }

    @OnClose
    public void onClose(Session session, @PathParam("groupId") Integer groupId, @PathParam("userId") Integer userId) throws IOException {
        messageService.removeMemberFromGroup(session, groupId, userId);
    }

    @OnError
    public void onError(Session session,Throwable error){
        error.printStackTrace();
    }

    */
/*@PostMapping("/send")
    public String sendMessage(@RequestBody Message message) {
        message.setMessageTime(LocalDateTime.now());
        messageService.save(message);
        return "Message sent and saved to database.";
    }*//*



    @GetMapping("/receive/{id}")
    public Message receiveMessage(@PathVariable Long id) {
        return messageService.getById(id);
    }

    // 获取某个聊天组的历史消息
    @GetMapping("/{groupId}")
    public List<Message> getMessagesByGroupId(@PathVariable Long groupId) {
        return messageService.getMessagesByGroupId(groupId);
    }
}*/
