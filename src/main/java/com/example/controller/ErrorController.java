package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Error Controller.
 * Created by yuheng.lin.
 * Date : 2017/5/15
 */
@Controller
@RequestMapping(value = "/error")
public class ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    private static String ERROR_VIEW_PREFIX = "/view/error";

    @RequestMapping("/unauthorized")
    public String unauthorizedRole(){
        logger.info("------没有权限-------");
        return "/view/error/403";
    }
}
