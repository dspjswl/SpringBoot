package com.example.service.impl;

import com.example.service.IOAuthService;
import com.example.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OAuth Service.
 *
 * @author yuheng.lin@hand-china.com
 */
@Service
public class OAuthService implements IOAuthService{
//    private Cache<String, String> cache;

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private ClientService clientService;

//    @Autowired
//    public OAuthService(RedisCacheManager cacheManager) {
//        this.cache = cacheManager.getCache("code-cache");
//    }

    @Override
    public void addAuthCode(String authCode, String username) {
        cacheUtil.add(authCode, username);
    }

    @Override
    public void addAccessToken(String accessToken, String username) {
        cacheUtil.add(accessToken, username);
    }

    @Override
    public String getUsernameByAuthCode(String authCode) {
        return cacheUtil.get(authCode);
    }

    @Override
    public String getUsernameByAccessToken(String accessToken) {
        return cacheUtil.get(accessToken);
    }

    @Override
    public boolean checkAuthCode(String authCode) {
        return cacheUtil.get(authCode) != null;
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        return cacheUtil.get(accessToken) != null;
    }

    @Override
    public boolean checkClientId(String clientId) {
        return clientService.findByClientId(clientId) != null;
    }

    @Override
    public boolean checkClientSecret(String clientSecret) {
        return clientService.findByClientSecret(clientSecret) != null;
    }

    @Override
    public long getExpireIn() {
        return 3600L;
    }
}
