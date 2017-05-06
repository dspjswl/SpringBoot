package com.example.service.impl;

import com.example.dto.Member;
import com.example.mapper.TestMapper;
import com.example.service.ITestService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * .
 * Created by yuheng.lin.
 * Date : 2017/5/5
 */
@Service
public class TestService implements ITestService{
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);
    @Autowired
    private TestMapper testMapper;
    @Override
    public String selectMemberCodeById(Long id) {
        logger.debug(id+"");
        return testMapper.selectMemberCodeById(id);
    }

    @Override
    public Member selectByPrimaryKey(Member member) {
        return testMapper.selectByPrimaryKey(member);
    }

    @Override
    public List<Member> selectAll() {
        PageHelper.startPage(1,2);
        return testMapper.selectAll();
    }
}
