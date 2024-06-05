package org.example.chatroom.controller;

import org.example.chatroom.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final WebSocketService webSocketService;

    @Autowired
    public WebSocketHandler(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 从URL路径中获取groupId和userId
        Integer groupId = getParameter(session, 3);
        Integer userId = getParameter(session, 4);
        // 调用服务层的onOpen方法
        webSocketService.onOpen(session, groupId, userId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 从URL路径中获取groupId和userId
        Integer groupId = getParameter(session, 3);
        Integer userId = getParameter(session, 4);
        // 调用服务层的onMessage方法
        webSocketService.onMessage(groupId, userId, message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 从URL路径中获取sid和userId
        Integer groupId = getParameter(session, 3);
        Integer userId = getParameter(session, 4);
        // 调用服务层的onClose方法
        webSocketService.onClose(session, groupId, userId);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 调用服务层的onError方法
        webSocketService.onError(exception);
    }

    // 从WebSocketSession的URI中提取参数值
    private Integer getParameter(WebSocketSession session, int index) {
        // 获取URI路径
        String path = session.getUri().getPath();

        // 分割路径
        String[] segments = path.split("/");

        // 返回指定索引的参数值
        return segments.length > index ? Integer.valueOf(segments[index]) : null;
    }
}
