package org.example.chatroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user WHERE username = #{username}")
    Optional<User> selectByUsername(@Param("username") String username);

    @Select("SELECT * FROM user WHERE email = #{email}")
    Optional<User> selectByEmail(@Param("email") String email);
}
