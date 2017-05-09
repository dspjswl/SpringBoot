package com.example;

import com.example.service.IRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * .
 * Created by yuheng.lin.
 * Date : 2017/5/9
 */
@Component
public class ApplicationStopDestory implements DisposableBean, ExitCodeGenerator {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationStopDestory.class);
    @Autowired
    private IRedisService redisService;

    @Override
    public void destroy() throws Exception {
        redisService.clearAllCache();
    }

    @Override
    public int getExitCode() {
        return 0;
    }
}
