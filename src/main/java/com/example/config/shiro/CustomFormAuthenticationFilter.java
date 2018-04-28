package com.example.config.shiro;

import com.example.dto.SysUser;
import com.example.dto.UserInfo;
import com.example.mapper.UserMapper;
import com.example.util.RedisTemplateUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    public static final String CAPTCHA_KEY = "captchaKey";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    private RedisTemplateUtil redisTemplateUtil;

    private static int TTL = 60 * 60;

    private UserMapper userMapper;

    public CustomFormAuthenticationFilter(RedisTemplateUtil redisTemplateUtil, UserMapper userMapper) {
        this.redisTemplateUtil = redisTemplateUtil;
        this.userMapper = userMapper;
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
            /*图形验证码验证*/
            doCaptchaValidate((HttpServletRequest) request, token);
            subject.login(token);//正常验证
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
//        String captcha = (String) request.getSession().getAttribute(
//                com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //从redis中获取验证码值
        String redisCaptcha = redisTemplateUtil.get(token.getCaptchaKey());
        //清除redis中该验证码信息
        redisTemplateUtil.remove("CAPTCHA_KEY");
        if (redisCaptcha == null || redisCaptcha != null && !redisCaptcha.equalsIgnoreCase(token.getCaptcha())) {
            throw new CaptchaNotMatchException("验证码错误或过期！");
        }
    }

    @Override
    protected CustomUsernamePasswordToken createToken(ServletRequest request,
                                                       ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        String captcha = getCaptcha(request);
        String captchaKey = getCaptchaKey(request);
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);

        return new CustomUsernamePasswordToken(username,
                password.toCharArray(), rememberMe, host, captcha, captchaKey);
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    private String getCaptchaKey(ServletRequest request) {
        return org.springframework.web.util.WebUtils.getCookie((HttpServletRequest) request, CAPTCHA_KEY).getValue();
    }

    //保存异常对象到request
    @Override
    protected void setFailureAttribute(ServletRequest request,
                                       AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae);
    }

    //重写登录成功重定向方法
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
        SysUser sysUser = userMapper.findUserByName(getUsername(request));
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(sysUser.getUsername());
        redisTemplateUtil.set(userInfo.getUsername(),userInfo,TTL);
        issueSuccessRedirect(request, response);
        //we handled the success redirect directly, prevent the chain from continuing:
        return false;
    }
}
