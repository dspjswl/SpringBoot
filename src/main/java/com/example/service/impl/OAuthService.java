package com.example.service.impl;

import com.example.service.IOAuthService;
import org.apache.shiro.cache.Cache;
import org.crazycake.shiro.RedisCache;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OAuth Service.
 *
 * @author yuheng.lin@hand-china.com
 */
@Service
public class OAuthService implements IOAuthService{
    private Cache<String, String> cache;

    @Autowired
    private ClientService clientService;

    @Autowired
    public OAuthService(RedisCacheManager cacheManager) {
        this.cache = cacheManager.getCache("code-cache");
    }

    @Override
    public void addAuthCode(String authCode, String username) {
        cache.put(authCode, username);
    }

    @Override
    public void addAccessToken(String accessToken, String username) {
        cache.put(accessToken, username);
    }

    @Override
    public String getUsernameByAuthCode(String authCode) {
        return cache.get(authCode);
    }

    @Override
    public String getUsernameByAccessToken(String accessToken) {
        return cache.get(accessToken);
    }

    @Override
    public boolean checkAuthCode(String authCode) {
        return cache.get(authCode) != null;
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        return cache.get(accessToken) != null;
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
