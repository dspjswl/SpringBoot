package com.example.service.impl;

import com.example.config.oauth.OAuthUser;
import com.example.mapper.OAuthUserMapper;
import com.example.service.IOAuthUserService;
import com.example.util.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OAuth User Service.
 *
 * @author yuheng.lin@hand-china.com
 */
@Service
public class OAuthUserService implements IOAuthUserService {

    @Autowired
    private OAuthUserMapper oAuthUserMapper;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public OAuthUser createUser(OAuthUser user) {
        return oAuthUserMapper.insert(user);
    }

    @Override
    public OAuthUser updateUser(OAuthUser user) {
        return oAuthUserMapper.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        oAuthUserMapper.delete(id);
    }

    @Override
    public void changePassword(Long id, String newPassword) {
        OAuthUser oAuthUser = oAuthUserMapper.findOne(id);
        oAuthUser.setPassword(newPassword);
        passwordHelper.encryptPassword(oAuthUser);
        oAuthUserMapper.update(oAuthUser);
    }

    @Override
    public OAuthUser findOne(Long id) {
        return oAuthUserMapper.findOne(id);
    }

    @Override
    public List<OAuthUser> findAll() {
        return oAuthUserMapper.findAll();
    }

    @Override
    public OAuthUser findByUsername(String username) {
        return oAuthUserMapper.findByUsername(username);
    }
}
