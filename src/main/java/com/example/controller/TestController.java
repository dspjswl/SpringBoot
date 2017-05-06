package com.example.controller;

import com.example.dto.Member;
import com.example.service.ITestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}