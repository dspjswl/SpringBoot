package com.example;

import com.example.service.IRedisService;
import com.example.service.impl.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner接口实现.
 * 主要用于application启动后进行一些数据的加载.
 * Created by yuheng.lin.
 * Date : 2017/5/9
 */
@Component
public class ApplicationStartupRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupRunner.class);
    @Autowired
    private IRedisService redisService;
    @Override
    public void run(String... args) throws Exception {
        if (logger.isDebugEnabled()){
            logger.debug("Loading all Cache...");
        }
        redisService.selectAllMember();
    }
}