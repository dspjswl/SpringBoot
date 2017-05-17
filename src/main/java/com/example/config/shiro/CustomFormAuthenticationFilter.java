package com.example.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 拓展FormAuthenticationFilter.
 * Created by yuheng.lin.
 * Date : 2017/5/17
 */
public class CustomFormAuthenticationFilter  extends FormAuthenticationFilter {
    private static final Logger LOG = LoggerFactory.getLogger(CustomFormAuthenticationFilter.class);

    public CustomFormAuthenticationFilter() {
    }
    @Override
    /**
     * 登录验证
     */
    protected boolean executeLogin(ServletRequest request,
                                   ServletResponse response) throws Exception {
        CustomUsernamePasswordToken token = createToken(request, response);
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);//正常验证
            /*图形验证码验证*/
            doCaptchaValidate((HttpServletRequest) request, token);
            LOG.info(token.getUsername()+"登录成功");
            return onLoginSuccess(token, subject, request, response);
        }catch (AuthenticationException e) {
            LOG.info(token.getUsername()+"登录失败--"+e);
            return onLoginFailure(token, e, request, response);
        }
    }

    // 验证码校验
    protected void doCaptchaValidate(HttpServletRequest request,
                                     CustomUsernamePasswordToken token) {
//session中的图形码字符串
        String captcha = (String) request.getSession().getAttribute(
                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
//比对
        if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
            throw new CaptchaNotMatchException("验证码错误！");
        }
    }

    @Override
    protected CustomUsernamePasswordToken createToken(ServletRequest request,
                                                       ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);

        return new CustomUsernamePasswordToken(username,
                password.toCharArray(), rememberMe, host, captcha);
    }

    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    //保存异常对象到request
    @Override
    protected void setFailureAttribute(ServletRequest request,
                                       AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae);
    }
}
