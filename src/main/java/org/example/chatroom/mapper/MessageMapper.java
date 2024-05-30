package org.example.chatroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.chatroom.entity.Message;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    @Select("SELECT * FROM chatmessage WHERE chat_group_id = #{groupId}")
    List<Message> selectByGroupId(Long groupId);
}
