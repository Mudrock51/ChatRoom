package org.example.chatroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.example.chatroom.entity.Message;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    @Select("SELECT * FROM chatmessage WHERE groupId = #{groupId}")
    List<Message> selectByGroupId(int groupId);

    @Insert("INSERT INTO chatmessage (groupId, userId, chatmessage.message_content) VALUES (#{groupId}, #{userId}, #{messageContent})")
    @Options(useGeneratedKeys = true, keyProperty = "messageId")
    void insertMessage(Message message);

    // 新增方法：获取所有消息
    @Select("SELECT * FROM chatmessage")
    List<Message> selectAllMessages();

    // 新增方法：删除消息
    @Delete("DELETE FROM chatmessage WHERE message_id = #{messageId}")
    int deleteMessageById(int messageId);
}
