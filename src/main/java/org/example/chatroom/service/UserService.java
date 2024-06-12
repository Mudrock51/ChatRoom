package org.example.chatroom.service;

import org.example.chatroom.dto.UserDTO;
import org.example.chatroom.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    UserDTO registerUser(UserDTO userDto);

    UserDTO loginUser(String username, String password);

    UserDTO forgetPassword(String email);

    void sendVerificationCode(String email);

    boolean validateVerificationCode(String email, String verificationCode);

    UserDTO resetPassword(String email, String newPassword);

    ResponseEntity<?> handleSendEmail(String email);

    ResponseEntity<?> handleVerifyCode(String email, String code);

    List<User> getAllUsers();

    User getUserById(int id);

    String generateVerificationCode();

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

}
