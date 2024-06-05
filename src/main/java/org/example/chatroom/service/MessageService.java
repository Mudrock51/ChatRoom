package org.example.chatroom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.chatroom.entity.Message;

import java.util.List;

public interface MessageService extends IService<Message> {

    //从数据库读取群组号为groupId对应的消息
    List<Message> getMessagesByGroupId(Long groupId);
}
