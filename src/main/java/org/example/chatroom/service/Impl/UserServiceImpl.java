package org.example.chatroom.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.example.chatroom.convert.UserConverter;
import org.example.chatroom.dto.UserDTO;
import org.example.chatroom.entity.User;
import org.example.chatroom.mapper.UserMapper;
import org.example.chatroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final Map<String, String> verificationCodes = new HashMap<>();

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO registerUser(UserDTO userDto) {
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
    public UserDTO loginUser(String username, String password) {
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

    @Override
    public UserDTO forgetPassword(String email) {
        User user = userMapper.selectByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email not registered"));

        sendVerificationCode(email);
        return UserConverter.convertUser(user);
    }

    @Override
    public void sendVerificationCode(String email) {
        // 生成并发送验证码
        String verificationCode = generateVerificationCode();
        verificationCodes.put(email, verificationCode);
        System.out.println("email: " + email + " verification code: " + verificationCode);
    }

    @Override
    public boolean validateVerificationCode(String email, String verificationCode) {
        return verificationCodes.containsKey(email) && verificationCode.equals(verificationCodes.get(email));
    }

    @Override
    public UserDTO resetPassword(String email, String newPassword) {
        User user = userMapper.selectByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email not registered"));

        user.setPassword(newPassword);
        userMapper.updateById(user);

        return UserConverter.convertUser(user);
    }

    @Override
    public ResponseEntity<?> handleSendEmail(String email) {
        UserDTO forgetedUser = forgetPassword(email);
        return new ResponseEntity<>(forgetedUser, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> handleVerifyCode(String email, String code) {
        boolean success = validateVerificationCode(email, code);
        if (success) {
            return ResponseEntity.ok("Code verified");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Verification failed");
        }
    }

    @Override
    public String generateVerificationCode() {
        // 简单生成验证码的方法
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }

    //获取所有用户信息
    @Override
    public List<User> getAllUsers() {
        return userMapper.selectList(null);
    }

    @Override
    public User getUserById(int id) {
        return userMapper.selectById(id);
    }

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void deleteUser(int id) {
        userMapper.deleteById(id);
    }


}
