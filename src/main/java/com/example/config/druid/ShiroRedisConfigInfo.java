package com.example.config.druid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Shiro Redis Config Information.
 *
 * @author yuheng.lin@hand-china.com
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class ShiroRedisConfigInfo {

    private String host;

    private int port;

    private int expire;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
