package com.example.config.shiro;

import com.example.config.oauth.OAuth2Token;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 自定义Authenticator.
 *
 * @author yuheng.lin@hand-china.com
 */ 
public class CustomModularRealmAuthenticator extends ModularRealmAuthenticator {

    private Logger log = LoggerFactory.getLogger(CustomModularRealmAuthenticator.class);

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken token)
            throws AuthenticationException {
        try {
            // 判断getRealms()是否返回为空
            assertRealmsConfigured();
            // 强制转换回自定义的CustomizedToken
//            CustomUsernamePasswordToken token = (CustomUsernamePasswordToken) authenticationToken;
            // 登录类型
            String loginType = getLoginType(token);
            // 所有Realm
            Collection<Realm> realms = getRealms();
            // 登录类型对应的所有Realm
            Collection<Realm> typeRealms = new ArrayList<>();
            for (Realm realm : realms) {
                if (realm.getName().contains(loginType))
                    typeRealms.add(realm);
            }

            // 判断是单Realm还是多Realm
            if (typeRealms.size() == 1)
                return doSingleRealmAuthentication(typeRealms.iterator().next(), token);
            else
                return doMultiRealmAuthentication(typeRealms, token);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    private String getLoginType(AuthenticationToken token) throws AuthenticationException{
        if (token instanceof CustomUsernamePasswordToken) {
            return "CustomShiroRealm";
        } else if (token instanceof OAuth2Token) {
            return "OAuth2Realm";
        } else {
            throw new AuthenticationException("Unsupported Token!");
        }
    }

}
