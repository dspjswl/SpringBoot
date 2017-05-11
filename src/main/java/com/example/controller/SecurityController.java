package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Security Controller.
 * Created by yuheng.lin
 * Date : 2017/5/11
 */
@Controller
public class SecurityController {
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
