package com.user.management.entity;

import com.user.management.enums.UserRoles;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class Users {
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
        Users users = (Users) o;
        return logout == users.logout && Objects.equals(id, users.id) && Objects.equals(username, users.username) && Objects.equals(password, users.password) && Objects.equals(createdAt, users.createdAt) && Objects.equals(updatedAt, users.updatedAt) && Objects.equals(roles, users.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, createdAt, updatedAt, roles, logout);
    }
}