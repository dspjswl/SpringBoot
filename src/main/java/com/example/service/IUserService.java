package com.example.service;

import com.example.dto.User;

/**
 * 用户服务类.
 * Created by yuheng.lin
 * Date : 2017/5/11
 */
public interface IUserService {
    User findUserByName(String username);
}
