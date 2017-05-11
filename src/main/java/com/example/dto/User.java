package com.example.dto;

import javax.persistence.Table;

/**
 * 用户类.
 * Created by yuheng.lin
 * Date : 2017/5/11
 */
@Table(name = "SYS_USER")
public class User {

    private String username;

    private String password;

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
}
