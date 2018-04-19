package com.example.config.shiro;

import com.example.config.oauth.OAuth2Realm;
import com.example.config.oauth.demo.DemoRealmInfo;
import com.example.config.oauth.github.GithubRealmInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Realm Info Handler.
 *
 * @author yuheng.lin@hand-china.com
 */
@Component
public class RealmInfoHandler {

    @Autowired
    DemoRealmInfo demoRealmInfo;
    @Autowired
    GithubRealmInfo githubRealmInfo;

    public void setRealmInfo(OAuth2Realm oAuth2Realm){
        switch (oAuth2Realm.getLoginObject()) {
        case "DEMO":
            BeanUtils.copyProperties(demoRealmInfo, oAuth2Realm);
            break;
        case "GITHUB":
            BeanUtils.copyProperties(githubRealmInfo, oAuth2Realm);
            break;
        default:
            break;
        }
    }
}
