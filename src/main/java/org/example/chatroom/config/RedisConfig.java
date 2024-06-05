package org.example.chatroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * 配置RedisTemplate以使用Jackson2JsonRedisSerializer进行序列化
     * @param redisConnectionFactory Redis连接工厂
     * @return 配置好的RedisTemplate实例
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 使用Jackson2JsonRedisSerializer替代默认的JdkSerializationRedisSerializer
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);

        // 设置key和value的序列化规则
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        // 设置hashKey和hashValue的序列化规则
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        return template;
    }

    /**
     * 配置StringRedisTemplate以使用String序列化器
     * @param redisConnectionFactory Redis连接工厂
     * @return 配置好的StringRedisTemplate实例
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }
}
