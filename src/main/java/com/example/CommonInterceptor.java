package com.example;

import com.example.service.IMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by yuheng.lin on 2017/5/3.
 */
public class CommonInterceptor implements HandlerInterceptor {

    @Autowired
    private IMessageService messageService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String path = httpServletRequest.getContextPath();
//        String scheme = httpServletRequest.getScheme();
        // getScheme()取的是项目的scheme，由于该项目未绑定443端口，故scheme是http，改用以下语句可以取到转发方的scheme
        // ,避免出现Mixed Content的错误
        String scheme = httpServletRequest.getHeader("x-forwarded-proto") == null ? "http" : httpServletRequest.getHeader("x-forwarded-proto");
        String serverName = httpServletRequest.getServerName();
        int port = httpServletRequest.getServerPort();
        String basePath = scheme + "://" + serverName;
        if (!"https".equals(scheme)) {
            basePath = basePath + ":" + port;
        }
        basePath = basePath + path;
        httpServletRequest.setAttribute("basePath", basePath);

        List<Map<String, String>> availableLocales = messageService.getAvailableLocales();
        httpServletRequest.setAttribute("locales", availableLocales);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
