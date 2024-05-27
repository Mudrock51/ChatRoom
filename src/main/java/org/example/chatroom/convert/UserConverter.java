package org.example.chatroom.convert;

import org.example.chatroom.mapper.User;
import org.example.chatroom.dto.UserDTO;

public class UserConverter {

    public static UserDTO convertUser(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setUserId(user.getUserId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setAvatarUrl(user.getAvatarUrl());
        userDto.setStatus(user.getStatus());
        return userDto;
    }

    public static User convertUser(UserDTO userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setAvatarUrl(userDto.getAvatarUrl());
        return user;
    }
}
