package com.example.controller;

import com.example.dto.Member;
import com.example.dto.SysUser;
import com.example.service.ITestService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuheng.lin on 2017/4/28.
 */
@RestController
@EnableAutoConfiguration
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private ITestService testService;
    @RequestMapping("/test")
    public HashMap<String, Object> index() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("asd", "affcccf");
        return map;
    }

    @RequestMapping("/testMapper")
    public String testMapper(@RequestParam Long id) {
        return testService.selectMemberCodeById(id);
    }

    @RequestMapping("/testMapper2")
    public Member testMapper2(@RequestParam Long id) {
        Member member = new Member();
        member.setMemberId(1L);
        return testService.selectByPrimaryKey(member);
    }

    @RequestMapping("/testMapper3")
    public List<Member> testMapper3() {
        if(logger.isDebugEnabled()){
            logger.debug("--------查询所有--------");
        }
        return testService.selectAll();
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public ModelAndView addUser() {
        return new ModelAndView("/register");
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ModelAndView register(@Valid SysUser user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (ObjectError error:errors) {
                sb.append(error.getDefaultMessage());
            }
            mav.setViewName("redirect:/register");
            redirectAttributes.addFlashAttribute("message",sb.toString());
            redirectAttributes.addFlashAttribute("username",user.getUsername());
            return mav;
        }
        //退出当前用户
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        mav.setViewName("redirect:/login");
        String s = (new Md5Hash(user.getPassword())).toString();
        return mav;
    }
}