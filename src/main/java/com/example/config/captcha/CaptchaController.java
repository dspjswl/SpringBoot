package com.example.config.captcha;

import com.example.util.CaptchaUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangmy on 2017/8/9.
 */
@RestController
public class CaptchaController {
    //超时时间三分钟
    private final int captchaTimeOut = 60 * 3;

    public static final String CAPTCHA_KEY = "captchaKey";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private CaptchaUtil captchaUtil;

    /**
     * 获取验证码(Google样式)
     */
    @RequestMapping(value = "/getCaptcha" ,method = RequestMethod.GET)
    @ResponseBody
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream servletOutputStream = null;
        try {
            //获取验证码字符串
            String text = defaultKaptcha.createText();
            String captchaKey;
            //获取验证码的key
            Cookie cookieCaptchaKey = WebUtils.getCookie(request, CAPTCHA_KEY);
            //若验证码的key为空，则根据UUID生成
            if (cookieCaptchaKey == null) {
                captchaKey = UUID.randomUUID().toString();
            } else {
                captchaKey = cookieCaptchaKey.getValue();
            }
            //验证码的key和验证码的值存入redis
            redisTemplate.opsForValue().set(captchaKey, text, captchaTimeOut, TimeUnit.SECONDS);
            //将key存储到本地cookie中
            Cookie cookie = new Cookie(CAPTCHA_KEY, captchaKey);
            response.addCookie(cookie);
            //生成图片
            BufferedImage bufferedImage = defaultKaptcha.createImage(text);
            servletOutputStream = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpg", servletOutputStream);
            servletOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (servletOutputStream != null) {
                try {
                    servletOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取验证码(自定义样式)
     */
    @RequestMapping(value = "/getCaptcha2" ,method = RequestMethod.GET)
    @ResponseBody
    public void getCaptcha2(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream servletOutputStream = null;
        try {
            Object[] obj = captchaUtil.createImage();
            String text = (String)obj[0];
            String captchaKey;
            //获取验证码的key
            Cookie cookieCaptchaKey = WebUtils.getCookie(request, CAPTCHA_KEY);
            //若验证码的key为空，则根据UUID生成
            if (cookieCaptchaKey == null) {
                captchaKey = UUID.randomUUID().toString();
            } else {
                captchaKey = cookieCaptchaKey.getValue();
            }
            //验证码的key和验证码的值存入redis
            redisTemplate.opsForValue().set(captchaKey, text, captchaTimeOut, TimeUnit.SECONDS);
            //将key存储到本地cookie中
            Cookie cookie = new Cookie(CAPTCHA_KEY, captchaKey);
            response.addCookie(cookie);
            //生成图片
            BufferedImage bufferedImage = (BufferedImage) obj[1];
            servletOutputStream = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpg", servletOutputStream);
            servletOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (servletOutputStream != null) {
                try {
                    servletOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}