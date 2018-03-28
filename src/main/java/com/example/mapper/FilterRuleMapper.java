package com.example.mapper;

import com.example.dto.FilterRule;
import com.example.util.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yuheng.lin@hand-china.com
 */
@Mapper
public interface FilterRuleMapper extends BaseMapper<FilterRule>{
    @Select("SELECT * from sys_filter_rule order by sort_no asc")
    List<FilterRule> getAllRuleList();
}
