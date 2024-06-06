package org.example.chatroom.service;

import org.example.chatroom.entity.Message;
import org.springframework.web.socket.WebSocketSession;

public interface WebSocketService {

    // 处理收到的消息
    void onMessage(Integer groupId, Integer userId, String message);

    // 处理连接开启
    void onOpen(WebSocketSession session,Integer groupId,Integer userId);

    // 处理连接关闭
    void onClose(WebSocketSession session,Integer groupId,Integer userId);

    // 处理错误
    void onError(Throwable error);
}
