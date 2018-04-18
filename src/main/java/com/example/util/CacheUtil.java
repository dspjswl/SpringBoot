package com.example.util;

import org.apache.shiro.cache.Cache;
import org.crazycake.shiro.RedisCacheManager;
import org.springframework.stereotype.Component;

/**
 * Cache工具类.
 *
 * @author yuheng.lin@hand-china.com
 */
@Component
public class CacheUtil {

    private Cache<String, String> cache;

    private CacheUtil(RedisCacheManager redisCacheManager) {
        this.cache = redisCacheManager.getCache("code-cache");
    }

    public void add(String k, String v) {
        cache.put(k, v);
    }

    public String get(String k) {
        return cache.get(k);
    }

    public void clear() {
        cache.clear();
    }
}
