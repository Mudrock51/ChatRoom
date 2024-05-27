package org.example.chatroom.service;


import org.example.chatroom.dto.UserDTO;
import org.springframework.stereotype.Service;


@Service
public interface UserService{

    UserDTO register(UserDTO userDto);
    UserDTO login(String username, String password);
}
