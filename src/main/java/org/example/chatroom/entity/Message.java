package org.example.chatroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("chatmessage")
public class Message {
    @TableId(value = "message_id", type = IdType.AUTO)
    private int messageId;
    private Integer groupId;
    private Integer userId;
    private String messageContent;

    @TableField(exist = false)
    private Integer onLineCount;

    public Message(){}

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int  messageId) {
        this.messageId = messageId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer chatGroupId) {
        this.groupId = chatGroupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Integer getOnLineCount() {
        return onLineCount;
    }

    public void setOnLineCount(Integer onLineCount) {
        this.onLineCount = onLineCount;
    }

}

