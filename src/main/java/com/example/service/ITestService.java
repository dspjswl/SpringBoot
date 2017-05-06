package com.example.service;

import com.example.dto.Member;

import java.util.List;

/**
 * .
 * Created by yuheng.lin.
 * Date : 2017/5/5
 */
public interface ITestService {
    public String selectMemberCodeById(Long id);

    public Member selectByPrimaryKey(Member member);

    public List<Member> selectAll();
}
