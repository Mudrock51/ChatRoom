package org.example.chatroom.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.example.chatroom.convert.UserConverter;
import org.example.chatroom.mapper.User;

import org.example.chatroom.dto.UserDTO;
import org.example.chatroom.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDTO register(UserDTO userDto) {
        if (userMapper.selectByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        } else if (userMapper.selectByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = UserConverter.convertUser(userDto);
        user.setPassword(userDto.getPassword());
        user.setStatus(User.Status.OFFLINE);

        userDto.setPassword(null);

        userMapper.insert(user);
        return UserConverter.convertUser(user);
    }

    @Override
    public UserDTO login(String username, String password) {

        User user = userMapper.selectByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        user.setLastLogin(new Timestamp(System.currentTimeMillis()));
        user.setStatus(User.Status.ONLINE);

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", username);
        userMapper.update(user, updateWrapper);

        return UserConverter.convertUser(user);

    }
}
