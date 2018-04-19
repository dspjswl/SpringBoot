package com.example.config.oauth.demo;

import com.example.config.shiro.BaseRealmInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Demo Realm Info.
 *
 * @author yuheng.lin@hand-china.com
 */
@Component
@ConfigurationProperties(prefix = "shiro.realm.demo")
public class DemoRealmInfo extends BaseRealmInfo {
}
