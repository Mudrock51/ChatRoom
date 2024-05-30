package org.example.chatroom.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.chatroom.entity.Message;
import org.example.chatroom.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService{

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Message> getMessagesByGroupId(Long groupId){
        return messageMapper.selectByGroupId(groupId);
    }
}
