package org.example.chatroom.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.example.chatroom.entity.Message;
import org.example.chatroom.entity.User;
import org.example.chatroom.mapper.UserMapper;
import org.example.chatroom.service.RedisService;
import org.example.chatroom.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    // 房间号 -> 组成员信息
    private static final ConcurrentHashMap<Integer, List<WebSocketSession>> groupMemberInfoMap = new ConcurrentHashMap<>();
    // 房间号 -> 在线人数
    private static final ConcurrentHashMap<Integer, Set<Integer>> onlineUserMap = new ConcurrentHashMap<>();

    // 处理收到的消息
    @Override
    public void onMessage(Integer groupId, Integer userId, String message) {
        List<WebSocketSession> sessionList = groupMemberInfoMap.get(groupId);
        Set<Integer> onlineUserList = onlineUserMap.get(groupId);

        // 从 message 中获取 content, Redis缓存的内容和数据库持久化的内容
        JSONObject jsonObject = JSON.parseObject(message);
        String content = jsonObject.getString("content");

        // 添加日志，打印传入的 message 字符串
        System.out.println("***\n" + "Received message: " + message + "\n***");
        System.out.println("***\n" + "Divide the content is: " + content + "\n***");

        try {
            // json字符串转Message对象
            Message msg = new Message();
            msg.setGroupId(groupId);
            msg.setUserId(userId);
            msg.setMessageContent(content);
            msg.setOnLineCount(onlineUserList.size());
            msg.setType("message");

            // 群组内广播消息
            sessionList.forEach(item -> {
                try {
                    // 这里广播的是Json格式String的消息内容
                    item.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // 存入 redis
            redisService.addMessage(groupId, msg);

        } catch (Exception e) {
            // 捕获并记录异常信息
            e.printStackTrace();
            System.out.println("Error parsing message: " + e.getMessage());
        }
    }

    // 处理连接建立
    @Override
    public void onOpen(WebSocketSession session, Integer groupId, Integer userId) {
        List<WebSocketSession> sessionList = groupMemberInfoMap.computeIfAbsent(groupId, k -> new ArrayList<>());
        Set<Integer> onlineUserList = onlineUserMap.computeIfAbsent(groupId, k -> new HashSet<>());
        onlineUserList.add(userId);
        sessionList.add(session);
        // 发送上线通知
        sendInfo(groupId, userId, onlineUserList.size(), "上线了~");
    }

    // 处理连接关闭
    @Override
    public void onClose(WebSocketSession session, Integer groupId, Integer userId) {
        List<WebSocketSession> sessionList = groupMemberInfoMap.get(groupId);
        sessionList.remove(session);
        Set<Integer> onlineUserList = onlineUserMap.get(groupId);
        onlineUserList.remove(userId);
        // 发送离线通知
        sendInfo(groupId, userId, onlineUserList.size(), "下线了~");
    }

    // 处理错误
    @Override
    public void onError(Throwable error) {
        // 处理错误
        error.printStackTrace();
    }

    // 发送通知消息
    private void sendInfo(Integer groupId, Integer userId, Integer onlineSum, String info) {
        // 获取当前用户信息
        User currentUser = userMapper.selectById(userId);

        // 构建消息对象
        Message msg = new Message();
        msg.setGroupId(groupId);
        msg.setOnLineCount(onlineSum);
        msg.setUserId(userId);
        msg.setMessageContent(currentUser.getUsername() + info);
        msg.setType("status");


        List<WebSocketSession> sessionList = groupMemberInfoMap.get(groupId);
        Set<Integer> onlineUserList = onlineUserMap.get(groupId);
        // 群组内广播消息
        sessionList.forEach(item -> {
            try {
                // json对象转字符串
                String text = JSONObject.toJSONString(msg);
                item.sendMessage(new TextMessage(text));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
