package com.example.controller;

import com.example.config.shiro.CaptchaNotMatchException;
import com.example.dto.SysUser;
import com.example.mapper.UserMapper;
import com.example.service.IShiroService;
import com.example.util.CacheUtil;
import com.netflix.discovery.converters.Auto;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.crazycake.shiro.RedisCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * .
 * Created by yuheng.lin
 * Date : 2017/5/14
 */
@Controller
public class ShiroController {

    private static final Logger logger = LoggerFactory.getLogger(ShiroController.class);

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IShiroService shiroService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Model model) {
//        String username = user.getUsername();
//        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        //验证是否登录成功
        if(currentUser.isAuthenticated()){
            logger.error("用户{}已经登录",currentUser.getPrincipal().toString());
            return "redirect:/user";
        }
        model.addAttribute("user", new SysUser());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, @Valid SysUser user, BindingResult bindingResult, RedirectAttributes redirectAttributes, String captcha) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : errors) {
                sb.append(error.getDefaultMessage());
            }
            redirectAttributes.addFlashAttribute("message", sb.toString());
            return "redirect:/login";
        }
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        String username = user.getUsername();
        Object e = request.getAttribute("shiroLoginFailure");
        if (e != null) {
            String exception = e.getClass().getName();
            System.out.println("exception=" + exception);
            if (exception != null) {
                if (UnknownAccountException.class.getName().equals(exception)) {
                    logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
                    redirectAttributes.addFlashAttribute("message", "未知账户");
                } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                    logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
                    redirectAttributes.addFlashAttribute("message", "密码不正确");
                } else if (LockedAccountException.class.getName().equals(exception)) {
                    logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
                    redirectAttributes.addFlashAttribute("message", "账户已锁定");
                } else if (ExcessiveAttemptsException.class.getName().equals(exception)) {
                    logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
                    redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
                } else if (AuthenticationException.class.getName().equals(exception)) {
                    //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
                    logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
                    ((AuthenticationException) e).printStackTrace();
                    redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
                } else if (CaptchaNotMatchException.class.getName().equals(exception)) {
                    //验证码不匹配
                    logger.info("对用户[" + username + "]进行登录验证..验证未通过,验证码不匹配");
                    redirectAttributes.addFlashAttribute("message", "验证码错误");
                }
            }
            //当第一次登录时填入正确的账户和密码，填个错误的验证码后提交时，
            // 第一次会重定向回login，但第二次登入时无论输入的信息正确与否，
            // 都会去到user页面，原因推测可能在于第一次登录的时候即使校验验证码
            // 不通过，但是用户名和密码是对的，所以导致SecurityUtils.getSubject()是
            // 已经登录的状态，致使第二次登录时并不会再去做校验了，可能是使用自
            // 定义的usernamePasswordToken还有哪里未设置到导致shiro内部的某个
            // 地方的校验一直在使用原生的usernamePasswordToken，现解决办法是在
            // 第一次校验的使用先使当前的用户登出
            currentUser.logout();
            return "redirect:/login";
        } else {
            return "redirect:/user";
        }
    }


//    @RequestMapping(value="/logout",method= RequestMethod.GET)
//    public String logout(RedirectAttributes redirectAttributes ){
//        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
//        SecurityUtils.getSubject().logout();
////        cacheUtil.clear();
//        redirectAttributes.addFlashAttribute("message", "您已安全退出");
//        return "redirect:/login";
//    }


    @RequestMapping("/user")
    public String getUserList(Map<String, Object> model) {
        model.put("userList", userMapper.getUserList());
        return "user";
    }

    @RequestMapping("/user/edit/{userid}")
    @RequiresPermissions(value = "user:edit", logical = Logical.AND)
    public String getUserList(@PathVariable int userid, Map<String, Object> model) {
        logger.info("------进入用户信息修改-------");
        model.put("userId", userid);
        return "user_edit";
    }

    @RequestMapping("/testPermission")
    @ResponseBody
    @RequiresRoles(value = {"admin", "manager"}, logical = Logical.OR)
    public String testPermission() {
        logger.info("------测试是否有权限-------");
        return "user_edit";
    }

    @RequestMapping("/updatePermission")
    @ResponseBody
    @RequiresRoles(value = {"admin"})
    public String updatePermission() {
        shiroService.updatePermission();
        logger.info("------更新权限成功-------");
        return "update permission successfully";
    }
}
