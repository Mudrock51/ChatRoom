package org.example.chatroom.controller;

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

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        UserDTO registeredUser = userService.registerUser(userDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        UserDTO loggedInUser = userService.loginUser(username, password);
        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestBody Map<String, String> request) {
        String action = request.get("action");
        String email = request.get("email");

        if (action == null || email == null) {
            return ResponseEntity.badRequest().body("Action and email are required");
        }

        try {
            switch (action) {
                case "sendEmail":
                    return userService.handleSendEmail(email);
                case "verifyCode":
                    String code = request.get("code");
                    if (code == null) {
                        return ResponseEntity.badRequest().body("Code is required for verification");
                    }
                    return userService.handleVerifyCode(email, code);
                default:
                    return ResponseEntity.badRequest().body("Invalid action");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/sendVerificationCode")
    public ResponseEntity<?> sendVerificationCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
         return userService.handleSendEmail(email);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<UserDTO> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newPassword = request.get("newPassword");
        UserDTO resetUser = userService.resetPassword(email, newPassword);
        return new ResponseEntity<>(resetUser, HttpStatus.OK);
    }
}
