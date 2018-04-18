package com.example.controller;

import com.example.config.oauth.OAuth2Realm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OAuth Controller.
 *
 * @author yuheng.lin@hand-china.com
 */
@Controller
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    DefaultWebSecurityManager dwsm;

    @RequestMapping("/demoLogin")
    @ResponseBody
    public String demoLogin() {
        Collection<Realm> realms = dwsm.getRealms();
        List<Realm> realmList = realms.stream().filter((realm) -> (realm.getName().contains("OAuth2Realm")))
                .collect(Collectors.toList());
        if (realmList != null && realmList.size() > 0) {
            OAuth2Realm oAuth2Realm = (OAuth2Realm) (realmList.get(0));
            oAuth2Realm.setLoginObject("DEMO");
            oAuth2Realm.setAccessTokenUrl("http://localhost:8070/demo/accessToken");
            oAuth2Realm.setClientId("c1ebe466-1cdc-4bd3-ab69-77c3561b9dee");
            oAuth2Realm.setClientSecret("d8346ea2-6017-43ed-ad68-19c0f971738b");
            oAuth2Realm.setRedirectUrl("http://localhost:8070/demo/oauth2-login");
            oAuth2Realm.setUserInfoUrl("http://localhost:8070/demo/userInfo");
        }
        return "/authorize?client_id=c1ebe466-1cdc-4bd3-ab69-77c3561b9dee&response_type=code&redirect_uri=http://localhost:8070/demo/oauth2-login";
    }

    @RequestMapping("/qqLogin")
    @ResponseBody
    public String qqLogin() {
        return "/authorize?client_id=c1ebe466-1cdc-4bd3-ab69-77c3561b9dee&response_type=code&redirect_uri=http://localhost:8070/demo/oauth2-login";
    }

    @RequestMapping("/githubLogin")
    @ResponseBody
    public String githubLogin() {
        Collection<Realm> realms = dwsm.getRealms();
        List<Realm> realmList = realms.stream().filter((realm) -> (realm.getName().contains("OAuth2Realm")))
                .collect(Collectors.toList());
        if (realmList != null && realmList.size() > 0) {
            OAuth2Realm oAuth2Realm = (OAuth2Realm) (realmList.get(0));
            oAuth2Realm.setLoginObject("GITHUB");
            oAuth2Realm.setAccessTokenUrl("https://github.com/login/oauth/access_token");
            oAuth2Realm.setClientId("de86cd3dce191ef8d721");
            oAuth2Realm.setClientSecret("5d9c3cf2c5ce15883bb5f43d119a2efd8a4c723f");
            oAuth2Realm.setRedirectUrl("http://192.168.202.18:8070/demo/oauth2-login");
            oAuth2Realm.setUserInfoUrl("https://api.github.com/user");
        }
        return "https://github.com/login/oauth/authorize?client_id=de86cd3dce191ef8d721&client_secret=5d9c3cf2c5ce15883bb5f43d119a2efd8a4c723f&redirect_uri=http://192.168.202.18:8070/demo/oauth2-login";
    }
}
