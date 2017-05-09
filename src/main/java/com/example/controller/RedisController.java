package com.example.controller;

import com.example.dto.Member;
import com.example.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Redis Controller.
 * Created by yuheng.lin.
 * Date : 2017/5/9
 */
@RestController
@RequestMapping(value = "/redis")
public class RedisController {

    @Autowired
    private IRedisService redisService;

    @RequestMapping("/test")
    public String test(){
        Member loaded = redisService.findById(1L);
        System.out.println("loaded="+loaded);
        Member cached = redisService.findById(1L);
        System.out.println("cached="+cached);
        loaded = redisService.findById(2L);
        System.out.println("loaded2="+loaded);
        return"ok";
    }


    @RequestMapping("/delete")
    public String delete(Long id){
        redisService.deleteFromCache(id);
        return"ok";
    }

    @RequestMapping("/test1")
    public String test1(){
        redisService.test();
        System.out.println("RedisController.test1()");
        return"ok";
    }
}
