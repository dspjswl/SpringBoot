package com.example.config.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 拓展UsernamePasswordToken,使其能够使用验证码.
 * Created by yuheng.lin.
 * Date : 2017/5/17
 */
public class CustomUsernamePasswordToken extends UsernamePasswordToken {
    //验证码字符串
    private String captcha;

    public CustomUsernamePasswordToken(String username, char[] password,
                                        boolean rememberMe, String host, String captcha) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
