package com.example.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色类.
 * Created by yuheng.lin
 * Date : 2017/5/13
 */
@Entity
@Table(name = "SYS_ROLE")
public class SysRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String roleName;
    @OneToMany(mappedBy = "role", fetch=FetchType.EAGER)
    private List<SysPermission> permissionList;// 一个角色对应多个权限
    @ManyToMany
    @JoinTable(name = "t_user_role", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
            @JoinColumn(name = "user_id") })
    private List<SysUser> userList;// 一个角色对应多个用户

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<SysPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<SysPermission> permissionList) {
        this.permissionList = permissionList;
    }

    public List<SysUser> getUserList() {
        return userList;
    }

    public void setUserList(List<SysUser> userList) {
        this.userList = userList;
    }

    @Transient
    public List<String> getPermissionsName() {
        List<String> list = new ArrayList<String>();
        List<SysPermission> perlist = getPermissionList();
        for (SysPermission per : perlist) {
            list.add(per.getPermissionName());
        }
        return list;
    }
}