package com.example.controller;

import com.example.dto.Member;
import com.example.dto.SysUser;
import com.example.service.IMessageService;
import com.example.service.ITestService;
import com.example.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuheng.lin on 2017/4/28.
 */
@Controller
@EnableAutoConfiguration
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private ITestService testService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IMessageService messageService;

    @ResponseBody
    @RequestMapping("/test")
    public HashMap<String, Object> index() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String welcome = messageService.getMessage("welcome");
        map.put("asd", welcome);
        return map;
    }

    @ResponseBody
    @RequestMapping("/testMapper")
    public String testMapper(@RequestParam Long id) {
        return testService.selectMemberCodeById(id);
    }

    @ResponseBody
    @RequestMapping("/testMapper2")
    public Member testMapper2(@RequestParam Long id) {
        Member member = new Member();
        member.setMemberId(1L);
        return testService.selectByPrimaryKey(member);
    }

    @ResponseBody
    @RequestMapping("/testMapper3")
    public List<Member> testMapper3() {
        if(logger.isDebugEnabled()){
            logger.debug("--------查询所有--------");
        }
        return testService.selectAll();
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String addUser() {
        return "register";
    }

    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ModelAndView register(@Valid SysUser user, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {
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
        String s = (new Md5Hash(user.getPassword())).toString();
        user.setPassword(s);
        userService.insert(user);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        redirectAttributes.addFlashAttribute("username",user.getUsername());
        mav.setViewName("redirect:/login");
        return mav;
    }

    @Autowired
    private DiscoveryClient client;
    @ResponseBody
    @RequestMapping(value = "/add" ,method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
        logger.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return r;
    }

    @ResponseBody
    @RequestMapping("/discovery")
    public String doDiscoveryService(){
        StringBuilder buf = new StringBuilder();
        List<String> serviceIds = client.getServices();
        if(!CollectionUtils.isEmpty(serviceIds)){
            for(String s : serviceIds){
                System.out.println("serviceId:" + s);
                List<ServiceInstance> serviceInstances =  client.getInstances(s);
                if(!CollectionUtils.isEmpty(serviceInstances)){
                    for(ServiceInstance si:serviceInstances){
                        buf.append("["+si.getServiceId() +" host=" +si.getHost()+" port="+si.getPort()+" uri="+si.getUri()+"]");
                    }
                }else{
                    buf.append("no service.");
                }
            }
        }


        return buf.toString();
    }
}