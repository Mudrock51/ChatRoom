package org.example.chatroom.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.sql.Timestamp;

import static com.baomidou.mybatisplus.annotation.IdType.AUTO;


@TableName("user")
public class User {

    @TableId(value = "user_id", type = AUTO)
    private Long userId;

    private String username;
    private String email;
    private String password;
    private String avatarUrl;
    private Timestamp lastLogin;
    private Timestamp createdAt;

    @TableField("status")
    private Status status;

    public enum Status {
        ONLINE("online"),
        OFFLINE("offline");

        @EnumValue
        private final String code;

        Status(String code) {
            this.code = code;
        }

        @JsonValue
        public String getCode() {
            return code;
        }

        @JsonCreator
        public static Status fromCode(String code) {
            for (Status status : values()) {
                if (status.code.equalsIgnoreCase(code)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown status: " + code);
        }
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
