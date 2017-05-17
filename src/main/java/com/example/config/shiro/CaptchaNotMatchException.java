package com.example.config.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 自定义验证码未匹配异常.
 * Created by yuheng.lin.
 * Date : 2017/5/17
 */
public class CaptchaNotMatchException extends AuthenticationException {

    public CaptchaNotMatchException() {
        super();
    }

    public CaptchaNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaNotMatchException(String message) {
        super(message);
    }

    public CaptchaNotMatchException(Throwable cause) {
        super(cause);
    }
}
