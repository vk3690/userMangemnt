package com.user.monitor.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class UsersInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String username;
    private String password;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    @Type(JsonBinaryType.class)
    @Column(name = "roles", columnDefinition = "jsonb")
    private Set<String> roles;

    @Column(name = "logout")
    private boolean logout;



    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) return false;
        UsersInfo usersInfo = (UsersInfo) o;
        return logout == usersInfo.logout && Objects.equals(id, usersInfo.id) && Objects.equals(username, usersInfo.username) && Objects.equals(password, usersInfo.password) && Objects.equals(createdAt, usersInfo.createdAt) && Objects.equals(updatedAt, usersInfo.updatedAt) && Objects.equals(roles, usersInfo.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, createdAt, updatedAt, roles, logout);
    }
}