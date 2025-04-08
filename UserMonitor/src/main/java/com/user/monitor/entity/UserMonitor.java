package com.user.monitor.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Objects;

@Entity
public class UserMonitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String action;

    private String username;

    @CreationTimestamp
    private Date createdAt;

    @Column(name = "message_convert_failed")
    private String messageConvertFailed;

    public UserMonitor(String action, String username) {
        this.action = action;
        this.username = username;
    }

    public UserMonitor(String messageConvertFailed) {
        this.messageConvertFailed=messageConvertFailed;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserMonitor that = (UserMonitor) o;
        return Objects.equals(id, that.id) && Objects.equals(action, that.action) && Objects.equals(username, that.username) && Objects.equals(createdAt, that.createdAt) && Objects.equals(messageConvertFailed, that.messageConvertFailed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, action, username, createdAt, messageConvertFailed);
    }
}
