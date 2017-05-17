package com.example.config.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Redis Session Config 共享session.
 * Created by yuheng.lin.
 * Date : 2017/5/17
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600) // session过期时间一小时
public class RedisSessionConfig {
}