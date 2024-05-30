package org.example.chatroom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.chatroom.entity.Message;

import java.util.List;

public interface MessageService extends IService<Message> {
    List<Message> getMessagesByGroupId(Long groupId);
}
