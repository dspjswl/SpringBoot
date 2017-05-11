package com.example.service.impl;

import com.example.dto.User;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserService Implement.
 * Created by yuheng.lin
 * Date : 2017/5/11
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByName(String username) {
        return userMapper.findUserByName(username);
    }
}
