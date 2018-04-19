package com.example.config.oauth.github;

import com.example.config.shiro.BaseRealmInfo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Github Realm Info.
 *
 * @author yuheng.lin@hand-china.com
 */
@Component
@ConfigurationProperties(prefix = "shiro.realm.github")
public class GithubRealmInfo extends BaseRealmInfo {
}
