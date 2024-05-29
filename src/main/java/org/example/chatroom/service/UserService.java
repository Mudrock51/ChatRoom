package org.example.chatroom.service;

import org.example.chatroom.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    UserDTO registerUser(UserDTO userDto);

    UserDTO loginUser(String username, String password);

    UserDTO forgetPassword(String email);

    void sendVerificationCode(String email);

    boolean validateVerificationCode(String email, String verificationCode);

    UserDTO resetPassword(String email, String newPassword);

    ResponseEntity<?> handleSendEmail(String email);

    ResponseEntity<?> handleVerifyCode(String email, String code);

    // 乱七八糟
    String generateVerificationCode();
}
