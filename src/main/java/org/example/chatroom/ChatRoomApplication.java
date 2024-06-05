package org.example.chatroom;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("org.example.chatroom.mapper")
@EnableAsync
public class ChatRoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatRoomApplication.class, args);
    }

}
