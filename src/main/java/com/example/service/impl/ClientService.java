package com.example.service.impl;

import com.example.dto.OAuthClient;
import com.example.mapper.ClientMapper;
import com.example.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OAuthClient Service.
 *
 * @author yuheng.lin@hand-china.com
 */
@Service
public class ClientService implements IClientService{

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public OAuthClient createClient(OAuthClient client) {
        return clientMapper.insertClient(client);
    }

    @Override
    public OAuthClient updateClient(OAuthClient client) {
        return clientMapper.updateClient(client);
    }

    @Override
    public void deleteClient(Long id) {
        clientMapper.deleteClient(id);
    }

    @Override
    public OAuthClient findOne(Long id) {
        return clientMapper.findOne(id);
    }

    @Override
    public List<OAuthClient> findAll() {
        return clientMapper.findAll();
    }

    @Override
    public OAuthClient findByClientId(String clientId) {
        return clientMapper.findByClientId(clientId);
    }

    @Override
    public OAuthClient findByClientSecret(String clientSecret) {
        return clientMapper.findByClientSecret(clientSecret);
    }
}
