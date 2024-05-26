package org.example.chatroom.service;

import org.example.chatroom.convert.UserConverter;
import org.example.chatroom.dao.User;
import org.example.chatroom.dao.UserRepository;
import org.example.chatroom.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDTO register(UserDTO userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        } else if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        User user = UserConverter.convertUser(userDto);
        user.setStatus(User.Status.OFFLINE);
        user = userRepository.save(user);
        return UserConverter.convertUser(user);
    }

    @Override
    public UserDTO login(String username, String password) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getPassword().equals(password)) {
            user.setLastLogin(new Timestamp(System.currentTimeMillis()));
            user.setStatus(User.Status.ONLINE);
            user = userRepository.save(user);
            return UserConverter.convertUser(user);
        } else {
            throw new RuntimeException("Invalid password");
        }
    }
}
