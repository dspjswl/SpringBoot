package com.example.mapper;

import com.example.dto.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * .
 * Created by yuheng.lin.
 * Date : 2017/5/18
 */
@Mapper
public interface UserRoleMapper {
    @Insert("insert into sys_user_role(user_id,role_id) values(#{user.id},(select id from sys_role where role_name='normal'))")
    int insert(@Param("user") SysUser user);
}
