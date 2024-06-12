package org.example.chatroom.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.chatroom.entity.Message;
import org.example.chatroom.mapper.MessageMapper;
import org.example.chatroom.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void saveMessage(Message message) {
        try {
            messageMapper.insertMessage(message);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // 从数据库获取历史消息
    @Override
    public List<Message> getMessagesByGroupId(int groupId){
        return messageMapper.selectByGroupId(groupId);
    }

    // 新增方法：获取所有消息
    @Override
    public List<Message> getAllMessages(){
        return messageMapper.selectAllMessages();
    }

    // 新增方法：删除消息
    @Override
    public boolean deleteMessageById(int messageId) {
        try {
            int result = messageMapper.deleteMessageById(messageId);
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
