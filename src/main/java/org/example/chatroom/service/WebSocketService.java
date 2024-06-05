package org.example.chatroom.service;

import org.springframework.web.socket.WebSocketSession;

public interface WebSocketService {
    void onMessage(Integer groupId,Integer userId,String message);

    void onOpen(WebSocketSession session,Integer groupId,Integer userId);

    void onClose(WebSocketSession session,Integer groupId,Integer userId);

    void onError(Throwable error);
}
