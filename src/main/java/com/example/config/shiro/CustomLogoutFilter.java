package com.example.config.shiro;

import com.example.util.CacheUtil;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义退出登录过滤器.
 *
 * @author yuheng.lin@hand-china.com
 */
//@Component
public class CustomLogoutFilter extends LogoutFilter{

    @Autowired
    private CacheUtil cacheUtil;

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);
        try {
            //清空缓存
            cacheUtil.clear();
            subject.logout();
        } catch (SessionException e) {
            e.printStackTrace();
        }
        issueRedirect(request, response, redirectUrl);
        return false;

    }
}
