package com.example.dto;

import javax.persistence.*;
import java.io.Serializable;

/**
 * .
 * Created by yuheng.lin
 * Date : 2017/5/13
 */
@Entity
@Table(name = "SYS_PERMISSION")
public class SysPermission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String permissionName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private SysRole role;// 一个权限对应一个角色

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }
}