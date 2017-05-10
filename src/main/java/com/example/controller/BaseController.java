package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by yuheng.lin on 2017/5/2.
 */
@Controller
public class BaseController {

    @RequestMapping("/hello")
    public ModelAndView welcome(ModelAndView mav) {
        mav.addObject("time",new Date());
        mav.setViewName("template");
        return mav;
    }

    /**
     * 主页跳转.
     * @return
     */
    @RequestMapping("/")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("index");
        return mav;
    }

    /**
     * 访问时获取basePath.
     * @return
     */
    @RequestMapping("/**/*.html")
    public ModelAndView getBasePath(ModelAndView mav) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String path = request.getContextPath();
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        //拼接上下文
        String basePath = scheme + "://" + serverName + ":" + port + path;
        //basePath信息放入attribute中
        mav.addObject("basePath", basePath);
//        request.setAttribute("basePath", basePath);
        //貌似入参中的mav的viewName为null时会返回原来访问的html，所以可以不用下面的setViewName
        //String requestURI = request.getRequestURI();
        //requestURI = requestURI.replace(path, "").replace(".html","");
        //mav.setViewName(null);
        return mav;
    }
}
