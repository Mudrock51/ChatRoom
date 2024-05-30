package org.example.chatroom.service.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.chatroom.entity.Message;
import org.example.chatroom.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<Long, Set<WebSocketSession>> groupSessions = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MessageService messageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long groupId = (Long) session.getAttributes().get("groupId");
        groupSessions.computeIfAbsent(groupId, k -> new HashSet<>()).add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        Message message = objectMapper.readValue(textMessage.getPayload(), Message.class);
        message.setMessageTime(LocalDateTime.now());

        // Save the message to the database
        messageService.save(message);

        // Forward the message to all sessions in the same group
        sendMessageToGroup(message.getChatGroupId(), message);
    }

    public void sendMessageToGroup(Long groupId, Message message) throws IOException {
        Set<WebSocketSession> sessions = groupSessions.get(groupId);
        if (sessions != null) {
            TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(message));
            for (WebSocketSession webSocketSession : sessions) {
                if (webSocketSession.isOpen()) {
                    webSocketSession.sendMessage(textMessage);
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long groupId = (Long) session.getAttributes().get("groupId");
        Set<WebSocketSession> sessions = groupSessions.get(groupId);
        if (sessions != null) {
            sessions.remove(session);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        Long groupId = (Long) session.getAttributes().get("groupId");
        Set<WebSocketSession> sessions = groupSessions.get(groupId);
        if (sessions != null) {
            sessions.remove(session);
        }
    }
}
