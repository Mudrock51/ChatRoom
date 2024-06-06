package org.example.chatroom.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.chatroom.entity.User;

@Getter
@Setter
public class UserDTO {
    private int userId;
    private String username;
    private transient String password; // 防止序列号
    private String email;
    private String avatarUrl;
    private User.Status status;
}
