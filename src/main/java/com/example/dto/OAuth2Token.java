package com.example.dto;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * OAuth2 Token.
 *
 * @author yuheng.lin@hand-china.com
 */
public class OAuth2Token extends UsernamePasswordToken implements AuthenticationToken {

    public OAuth2Token(String authCode) {
        this.authCode = authCode;
    }

    private String authCode;
    private String principal;

    public OAuth2Token(String username, String password) {
        super(username, password, false, null);
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return authCode;
    }
}
