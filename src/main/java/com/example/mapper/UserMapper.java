package com.example.mapper;

import com.example.dto.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * UserMapper.
 * Created by yuheng.lin
 * Date : 2017/5/11
 */
@Mapper
public interface UserMapper {
//    @Select("SELECT * FROM sys_user where username = #{username}")
    SysUser findUserByName(@Param("username") String username);

    @Select("SELECT * FROM sys_user")
    List<SysUser> getUserList();
}
