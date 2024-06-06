package org.example.chatroom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.chatroom.entity.Message;
import org.example.chatroom.entity.MessageType;

import java.util.List;

public interface MessageService extends IService<Message> {

    // 保存消息
    void saveMessage(Message message);

    //从数据库读取群组号为groupId对应的消息
    List<Message> getMessagesByGroupId(int groupId);
}
