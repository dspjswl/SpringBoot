package com.example.config.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义ShiroHttpServletResponse(去除url中出现的jessionId).
 * Created by yuheng.lin.
 * Date : 2017/5/16
 */
public class CustomShiroHttpServletResponse extends ShiroHttpServletResponse{
    public CustomShiroHttpServletResponse(HttpServletResponse wrapped, ServletContext context, ShiroHttpServletRequest request) {
        super(wrapped, context, request);
    }

    /**
     * Return the specified URL with the specified session identifier suitably encoded.
     *
     * @param url       URL to be encoded with the session id
     * @param sessionId Session id to be included in the encoded URL
     * @return the url with the session identifer properly encoded.
     */
    @Override
    protected String toEncoded(String url, String sessionId) {

        if ((url == null) || (sessionId == null))
            return (url);

        String path = url;
        String query = "";
        String anchor = "";
        int question = url.indexOf('?');
        if (question >= 0) {
            path = url.substring(0, question);
            query = url.substring(question);
        }
        int pound = path.indexOf('#');
        if (pound >= 0) {
            anchor = path.substring(pound);
            path = path.substring(0, pound);
        }
        StringBuilder sb = new StringBuilder(path);
        //注释以下代码防止URL自带jessionId
//        if (sb.length() > 0) { // session id param can't be first.
//            sb.append(";");
//            sb.append(DEFAULT_SESSION_ID_PARAMETER_NAME);
//            sb.append("=");
//            sb.append(sessionId);
//        }
        sb.append(anchor);
        sb.append(query);
        return (sb.toString());

    }
}
