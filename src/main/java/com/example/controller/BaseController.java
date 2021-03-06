package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yuheng.lin on 2017/5/2.
 */
@Controller
public class BaseController {

    @RequestMapping("/hello")
    public ModelAndView welcome(ModelAndView mav) {
        mav.addObject("time", new Date());
        mav.setViewName("template");
        return mav;
    }

    /**
     * 主页跳转.
     *
     * @return
     */
    @RequestMapping("/")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("index");
        return mav;
    }

    /**
     * 访问时获取basePath.
     *
     * @return
     */
    @RequestMapping("/**/*.html")
    public ModelAndView getBasePath(ModelAndView mav) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String path = request.getContextPath();
//        String scheme = request.getScheme();
        // getScheme()取的是项目的scheme，由于该项目未绑定443端口，故scheme是http，改用以下语句可以取到转发方的scheme
        // ,避免出现Mixed Content的错误
        String scheme = request.getHeader("x-forwarded-proto") == null ? "http" : request.getHeader("x-forwarded-proto");
        String serverName = request.getServerName();
        int port = request.getServerPort();
        //拼接上下文
        String basePath = scheme + "://" + serverName;
        if (!"https".equals(scheme)) {
            basePath = basePath + ":" + port;
        }
        basePath = basePath + path;
        //basePath信息放入attribute中
        mav.addObject("basePath", basePath);
//        request.setAttribute("basePath", basePath);
        //貌似入参中的mav的viewName为null时会返回原来访问的html，所以可以不用下面的setViewName
        //String requestURI = request.getRequestURI();
        //requestURI = requestURI.replace(path, "").replace(".html","");
        //mav.setViewName(null);
        return mav;
    }

    @RequestMapping("/changeLocale")
    public void changeLocale(HttpServletRequest request, HttpServletResponse response, String lang) {
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        Locale locale = new Locale(lang);
        localeResolver.setLocale(request, response, locale);

        return ;
    }
}
