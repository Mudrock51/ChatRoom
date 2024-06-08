package org.example.chatroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("chatmessage")
public class Message {
    @TableId(value = "message_id", type = IdType.AUTO)
    private int messageId;
    private int groupId;
    private int userId;
    private String messageContent;

    @TableField(exist = false)
    private String Type;

    @TableField(exist = false)
    private int onLineCount;
}

