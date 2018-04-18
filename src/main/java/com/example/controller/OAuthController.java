package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * OAuth Controller.
 *
 * @author yuheng.lin@hand-china.com
 */
@Controller
@RequestMapping("/oauth")
public class OAuthController {

    @RequestMapping("/demoLogin")
    @ResponseBody
    public String demoLogin() {

        return "/authorize?client_id=c1ebe466-1cdc-4bd3-ab69-77c3561b9dee&response_type=code&redirect_uri=http://localhost:8070/demo/oauth2-login";
    }

    @RequestMapping("/qqLogin")
    @ResponseBody
    public String qqLogin() {
        return "/authorize?client_id=c1ebe466-1cdc-4bd3-ab69-77c3561b9dee&response_type=code&redirect_uri=http://localhost:8070/demo/oauth2-login";
    }
}
