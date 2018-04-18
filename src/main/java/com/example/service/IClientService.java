package com.example.service;

import com.example.config.oauth.OAuthClient;

import java.util.List;

/**
 * @author yuheng.lin@hand-china.com
 */
public interface IClientService {
    public OAuthClient createClient(OAuthClient client);// 创建客户端
    public OAuthClient updateClient(OAuthClient client);// 更新客户端
    public void deleteClient(Long id);// 删除客户端
    OAuthClient findOne(Long id);// 根据id查找客户端
    List<OAuthClient> findAll();// 查找所有
    OAuthClient findByClientId(String clientId);// 根据客户端id查找客户端
    OAuthClient findByClientSecret(String clientSecret);//根据客户端安全KEY查找客户端
}
