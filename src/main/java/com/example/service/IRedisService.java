package com.example.service;

import com.example.dto.Member;

import java.util.List;

/**
 * Redis Service.
 * Created by yuheng.lin.
 * Date : 2017/5/9
 */
public interface IRedisService {
    public void test();

    public Member findById(Long id);

    public void deleteFromCache(Long id);

    public List<Member> selectAllMember();

    public void clearAllCache();
}
