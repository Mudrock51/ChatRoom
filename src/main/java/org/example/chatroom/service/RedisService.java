package org.example.chatroom.service;

import org.example.chatroom.entity.Message;

import java.util.List;

public interface RedisService {
    /**
     * 添加消息到 Redis 缓存，并异步持久化到 MySQL
     *
     * @param groupId 群组 ID
     * @param message 消息对象
     */
    void addMessage(Integer groupId, Message message);

    /**
     * 获取 Redis 缓存中的最近 100 条消息
     *
     * @param groupId 群组 ID
     * @return 消息列表
     */
    List<Object> getRecentMessages(Long groupId);

}
