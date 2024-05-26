package org.example.chatroom.controller;

import org.example.chatroom.Response;
import org.example.chatroom.dto.UserDTO;
import org.example.chatroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*注册接口*/
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        UserDTO registeredUser = userService.register(userDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    /*登录接口*/
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        System.out.println("username: " + username + " password: " + password);
        UserDTO loggedInUser = userService.login(username, password);
        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }
}
