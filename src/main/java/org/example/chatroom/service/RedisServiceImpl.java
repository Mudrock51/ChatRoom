package org.example.chatroom.service;

import org.example.chatroom.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableAsync
@Service
public class RedisServiceImpl implements RedisService {

    private static final String CHAT_MESSAGE_KEY_PREFIX = "chat:messages:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private MessageService messageService;

    /**
     * 添加消息到 Redis 缓存，并异步持久化到 MySQL
     *
     * @param groupId 群组 ID
     * @param message 消息对象
     */
    public void addMessage(Integer groupId, Message message) {
        String key = CHAT_MESSAGE_KEY_PREFIX + groupId;
        message.setGroupId(groupId);
        // 将消息缓存到 Redis 列表
        redisTemplate.opsForList().leftPush(key, message);
        // 设置缓存过期时间
        redisTemplate.expire(key, 1, TimeUnit.DAYS);
        // 保留最近的 100 条消息
        trimMessageList(key, 100);
        // 异步保存消息到 MySQL
        saveMessageAsync(message);
    }

    /**
     * 异步保存消息到 MySQL
     *
     * @param message 消息对象
     */
    @Async
    public void saveMessageAsync(Message message) {
        //TODO 这里消息消失了
        messageService.saveMessage(message);
    }

    /**
     * 获取 Redis 缓存中的最近 100 条消息
     *
     * @param groupId 群组 ID
     * @return 消息列表
     */
    public List<Object> getRecentMessages(Long groupId) {
        String key = CHAT_MESSAGE_KEY_PREFIX + groupId;
        return redisTemplate.opsForList().range(key, 0, 99);
    }

    /**
     * 保留 Redis 缓存中的指定数量的消息
     *
     * @param key Redis 键
     * @param maxSize 最大数量
     */
    private void trimMessageList(String key, int maxSize) {
        redisTemplate.opsForList().trim(key, 0, maxSize - 1);
    }
}
