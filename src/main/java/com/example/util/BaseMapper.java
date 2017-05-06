package com.example.util;


import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * BaseMapper.
 * Created by yuheng.lin.
 * Date : 2017/5/5
 */
/*注意！！！该mapper不能被扫描到,即不要和其它mapper放在一起*/
public interface BaseMapper<T> extends Mapper<T>,MySqlMapper {
}
