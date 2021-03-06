package com.example.service.impl;

import com.example.dto.Member;
import com.example.mapper.TestMapper;
import com.example.service.IRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Redis Service Implement.
 * Created by yuheng.lin.
 * Date : 2017/5/9
 */
@Service
@CacheConfig(cacheNames = "memberCache")//此处指定后方法上的cache注解的value值即为该值,若方法上指定了value则以方法上为准
public class RedisService implements IRedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);


    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private TestMapper testMapper;

    @Override
    public void test() {

        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("mykey4", "random1="+Math.random());
        System.out.println(valueOperations.get("mykey4"));
    }

    //keyGenerator="myKeyGenerator"
    @Cacheable //缓存,这里没有指定key.
    @Override
    public Member findById(Long id) {
        System.err.println("DemoInfoServiceImpl.findById()=========从数据库中进行获取的....id="+id);
        return testMapper.selectByPrimaryKey(id);
    }

    @CacheEvict
    @Override
    public void deleteFromCache(Long id) {
        System.out.println("DemoInfoServiceImpl.delete().从缓存中删除.");
    }

    @Override
    @Cacheable(value = "memberCache2") //以这里的value为准
    public List<Member> selectAllMember() {
        List<Member> members = testMapper.selectAll();
        return members;

    }

    @Override
    @CacheEvict(allEntries = true)
    public void clearAllCache() {
        if (logger.isDebugEnabled()){
            logger.debug("Cleaning all cache...");
        }
    }
}
