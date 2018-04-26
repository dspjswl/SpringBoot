package com.example.config.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletException;
import java.util.Properties;

/**
 * 验证码配置.
 * Created by yuheng.lin.
 * Date : 2017/5/17
 */
@Configuration
public class CaptchaConfig {

    @Value("${captcha.border}")
    private String border;

    @Value("${captcha.border.color}")
    private String borderColor;

    @Value("${captcha.session.key}")
    private String skey;

    @Value("${captcha.textproducer.impl}")
    private String tpImpl;

    @Value("${captcha.textproducer.font.color}")
    private String fcolor;

    @Value("${captcha.textproducer.font.size}")
    private String fsize;

    @Value("captcha.textproducer.font.names")
    private String fnames;

    @Value("${captcha.obscurificator.impl}")
    private String obscurificator;

    @Value("${captcha.noise.impl}")
    private String noise;

    @Value("${captcha.image.width}")
    private String width;

    @Value("${captcha.image.height}")
    private String height;

    @Value("${captcha.textproducer.char.length}")
    private String clength;

    @Value("${captcha.textproducer.char.space}")
    private String cspace;

    @Value("${captcha.background.clear.from}")
    private String from;

    @Value("${captcha.background.clear.to}")
    private String to;

    @Value("${captcha.word.impl}")
    private String word;

//    @Bean
//    public ServletRegistrationBean servletRegistrationBean() throws ServletException{
//        KaptchaServlet kaptchaServlet = new KaptchaServlet();
//        ServletRegistrationBean servlet = new ServletRegistrationBean(kaptchaServlet,"/images/kaptcha.jpg");
//        servlet.addInitParameter("kaptcha.border", border);//无边框
//        servlet.addInitParameter("kaptcha.border.color", borderColor);
//        servlet.addInitParameter("kaptcha.session.key", skey);//session key
//        servlet.addInitParameter("kaptcha.textproducer.impl", tpImpl);
//        servlet.addInitParameter("kaptcha.textproducer.font.color", fcolor);
//        servlet.addInitParameter("kaptcha.textproducer.font.size", fsize);
//        servlet.addInitParameter("kaptcha.obscurificator.impl", obscurificator);
//        servlet.addInitParameter("kaptcha.noise.impl", noise);
//        servlet.addInitParameter("kaptcha.image.width", width);
//        servlet.addInitParameter("kaptcha.image.height", height);
//        servlet.addInitParameter("kaptcha.textproducer.char.length", clength);
//        servlet.addInitParameter("kaptcha.textproducer.char.space", cspace);
//        servlet.addInitParameter("kaptcha.background.clear.from", from); //和登录框背景颜色一致
//        servlet.addInitParameter("kaptcha.background.clear.to", to);
//        servlet.addInitParameter("kaptcha.word.impl", word);
//        return servlet;
//    }

    @Bean
    public DefaultKaptcha getKaptchaBean() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", border);//无边框
        properties.setProperty("kaptcha.border.color", borderColor);
        properties.setProperty("kaptcha.session.key", skey);//session key
        properties.setProperty("kaptcha.textproducer.impl", tpImpl);
        properties.setProperty("kaptcha.textproducer.font.color", fcolor);
        properties.setProperty("kaptcha.textproducer.font.size", fsize);
        properties.setProperty("kaptcha.textproducer.font.names", fnames);
        properties.setProperty("kaptcha.obscurificator.impl", obscurificator);
        properties.setProperty("kaptcha.noise.impl", noise);
        properties.setProperty("kaptcha.image.width", width);
        properties.setProperty("kaptcha.image.height", height);
        properties.setProperty("kaptcha.textproducer.char.length", clength);
        properties.setProperty("kaptcha.textproducer.char.space", cspace);
        properties.setProperty("kaptcha.background.clear.from", from); //和登录框背景颜色一致
        properties.setProperty("kaptcha.background.clear.to", to);
        properties.setProperty("kaptcha.word.impl", word);
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
