package com.example.service.impl;

import com.example.dto.SysUser;
import com.example.mapper.UserMapper;
import com.example.mapper.UserRoleMapper;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserService Implement.
 * Created by yuheng.lin
 * Date : 2017/5/11
 */
@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public SysUser findUserByName(String username) {
        return userMapper.findUserByName(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUser insert(SysUser user) {
        userMapper.insert(user);
        int result = userRoleMapper.insert(user);
        return user;
    }
}
