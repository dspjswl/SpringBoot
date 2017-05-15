package com.example.dto;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户类.
 * Created by yuheng.lin
 * Date : 2017/5/11
 */
@Table(name = "SYS_USER")
public class User {

    @Id
    private Long id;

    private String username;

    private String password;

    private String role;

    private int access;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }
}
