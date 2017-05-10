package com.example.config.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 日志切面类.
 * Created by yuheng.lin.
 * Date : 2017/5/10
 */
@Aspect
@Configuration
public class LogAspect {
    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    ObjectMapper mapper = new ObjectMapper();

    @Pointcut("execution(public * com.example.controller..*.*(..))")
    public void handleLog() {
    }

    @Before("handleLog()")
    public void doBefore(JoinPoint joinPoint) throws JsonProcessingException {
        logger.info("====进入切面====");
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        String path = request.getContextPath();
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String basePath = scheme + "://" + serverName + ":" + port + path;
        logger.info("BASE_PATH : " + basePath);
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        Map<String, String[]> map = request.getParameterMap();

//        Object[] args = joinPoint.getArgs();
        // Convert object to JSON string
        logger.info("Parameters : " + mapper.writeValueAsString(map));
    }

    @AfterReturning(returning = "ret", pointcut = "handleLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        String json = mapper.writeValueAsString(ret);
        logger.info("RESPONSE : " + json);
    }
}
