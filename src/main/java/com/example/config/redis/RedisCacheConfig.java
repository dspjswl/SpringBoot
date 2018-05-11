package com.example.config.redis;


import com.example.util.RedisTemplateUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;

/**
 * redis 缓存配置.
 * Created by yuheng.lin.
 * Date : 2017/5/9
 * <p>
 * 注意：RedisCacheConfig这里也可以不用继承：CachingConfigurerSupport，也就是直接一个普通的Class就好了；
 * <p>
 * 这里主要我们之后要重新实现 key的生成策略，只要这里修改KeyGenerator，其它位置不用修改就生效了。
 * <p>
 * 普通使用普通类的方式的话，那么在使用@Cacheable的时候还需要指定KeyGenerator的名称;这样编码的时候比较麻烦。
 */
@Configuration
//@EnableCaching//启用缓存，这个注解很重要；
public class RedisCacheConfig extends CachingConfigurerSupport {

    private static String REDIS_NAME_SPACE = "dspjswl";

    // Spring boot 1.0使用
//    @Bean
//    public CacheManager getCacheManager(RedisTemplate<?, ?> redisTemplate) {
//        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//        cacheManager.setDefaultExpiration(10);
//        return cacheManager;
//    }

    /**
     * 缓存管理器.
     *
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(factory);
        return builder.build();
    }


    /**
     * redis模板操作类,类似于jdbcTemplate的一个类;
     * <p>
     * 虽然CacheManager也能获取到Cache对象，但是操作起来没有那么灵活；
     * <p>
     * 这里在扩展下：RedisTemplate这个类不见得很好操作，我们可以在进行扩展一个我们
     * <p>
     * 自己的缓存类，比如：RedisStorage类;
     *
     * @param factory : 通过Spring进行注入，参数在application.properties进行配置；
     * @return
     */
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
//        StringRedisTemplate redisTemplate = new StringRedisTemplate(factory);
//        //key序列化方式,但是如果方法上有Long等非String类型的话，会报类型转换错误
//        //所以在没有自己定义key生成策略的时候，以下这个代码建议不要这么写，可以不配置或者自己实现ObjectRedisSerializer
//        RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long类型不可以会出现异常信息;
//        redisTemplate.setKeySerializer(redisSerializer);
////        redisTemplate.setHashKeySerializer(redisSerializer);
////        redisTemplate.setValueSerializer(redisSerializer);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setKeySerializer(stringSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setKeySerializer(stringSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplateUtil redisUtil(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        RedisTemplateUtil redisUtil = new RedisTemplateUtil();
        redisUtil.setRedisTemplate(redisTemplate);
        redisUtil.setStringRedisTemplate(stringRedisTemplate);
        redisUtil.setNamespace(REDIS_NAME_SPACE);
        return redisUtil;
    }

    /**
     * 自定义key.
     * 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key,即使@Cacheable中的value属性一样，key也会不一样。
     */
    @Override
    public KeyGenerator keyGenerator() {
        System.out.println("RedisCacheConfig.keyGenerator()");
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                // This will generate a unique key of the class name, the method name
                //and all method parameters appended.
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                System.out.println("keyGenerator=" + sb.toString());
                return sb.toString();
            }
        };
    }
}
