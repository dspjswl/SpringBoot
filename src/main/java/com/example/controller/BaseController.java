package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by yuheng.lin on 2017/5/2.
 */
@Controller
public class BaseController {

    @RequestMapping("/hello")
    public String welcome(ModelMap model) {
        model.put("time", new Date());
        model.put("hello", new Date());
        return "template";
    }

    /**
     * 主页跳转.
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 访问时获取basePath.
     * @return
     */
    @RequestMapping("/**/*.html")
    public String getBasePath() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String path = request.getContextPath();
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        //拼接上下文
        String basePath = scheme + "://" + serverName + ":" + port + path;
        //basePath信息放入attribute中
        request.setAttribute("basePath", basePath);
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replace(path, "").replace(".html","");
        return requestURI;
    }
}
