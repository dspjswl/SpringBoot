package com.example.mapper;

import com.example.dto.Member;
import com.example.util.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 测试用Mapper.
 * Created by yuheng.lin.
 * Date : 2017/5/5
 */
public interface TestMapper extends BaseMapper<Member> {
    @Select("select market_id from mm_member where member_id = #{id}")
    public String selectMemberCodeById(@Param("id") Long id);

    public Member selectMemberById(@Param("id") Long id);

}
