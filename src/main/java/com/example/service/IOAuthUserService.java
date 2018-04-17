package com.example.service;

import com.example.dto.OAuthUser;

import java.util.List;

/**
 * @author yuheng.lin@hand-china.com
 */
public interface IOAuthUserService {
    public OAuthUser createUser(OAuthUser user);
    public OAuthUser updateUser(OAuthUser user);
    public void deleteUser(Long id);

    public void changePassword(Long id, String newPassword);

    OAuthUser findOne(Long id);
    List<OAuthUser> findAll();
    public OAuthUser findByUsername(String username);
}
