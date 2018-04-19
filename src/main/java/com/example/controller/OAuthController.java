package com.example.controller;

import com.example.config.oauth.OAuth2Realm;
import com.example.config.shiro.RealmInfoHandler;
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

    private DefaultWebSecurityManager dwsm;

    private List<Realm> oAuth2RealmList;

    @Autowired
    private RealmInfoHandler realmInfoHandler;

    @Autowired
    OAuthController(DefaultWebSecurityManager dwsm){
        this.dwsm = dwsm;
        Collection<Realm> realms = dwsm.getRealms();
        this.oAuth2RealmList = realms.stream().filter((realm) -> (realm.getName().contains("OAuth2Realm")))
                .collect(Collectors.toList());
    }

    @RequestMapping("/demoLogin")
    @ResponseBody
    public String demoLogin() {
        OAuth2Realm oAuth2Realm = null;
        if (oAuth2RealmList != null && oAuth2RealmList.size() > 0) {
            oAuth2Realm = (OAuth2Realm) (oAuth2RealmList.get(0));
            oAuth2Realm.setLoginObject("DEMO");
            realmInfoHandler.setRealmInfo(oAuth2Realm);
        }
        return "/authorize?client_id="+oAuth2Realm.getClientId()+"&response_type=code&redirect_uri="+oAuth2Realm.getRedirectUrl();
    }

    @RequestMapping("/qqLogin")
    @ResponseBody
    public String qqLogin() {
        return "/authorize?client_id=c1ebe466-1cdc-4bd3-ab69-77c3561b9dee&response_type=code&redirect_uri=http://localhost:8070/demo/oauth2-login";
    }

    @RequestMapping("/githubLogin")
    @ResponseBody
    public String githubLogin() {
        OAuth2Realm oAuth2Realm = null;
        if (oAuth2RealmList != null && oAuth2RealmList.size() > 0) {
            oAuth2Realm = (OAuth2Realm) (oAuth2RealmList.get(0));
            oAuth2Realm.setLoginObject("GITHUB");
            realmInfoHandler.setRealmInfo(oAuth2Realm);
        }
        return "https://github.com/login/oauth/authorize?client_id="+oAuth2Realm.getClientId()+"&client_secret="+oAuth2Realm.getClientSecret()+"&redirect_uri="+oAuth2Realm.getRedirectUrl();
    }
}
