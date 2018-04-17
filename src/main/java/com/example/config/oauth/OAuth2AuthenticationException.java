package com.example.config.oauth;

import org.apache.shiro.authc.AuthenticationException;

/**
 * OAuth2 Authentication Exception.
 *
 * @author yuheng.lin@hand-china.com
 */ 
public class OAuth2AuthenticationException extends AuthenticationException {

    public OAuth2AuthenticationException(Throwable cause) {
        super(cause);
    }
}
