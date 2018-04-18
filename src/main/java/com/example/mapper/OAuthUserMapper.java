package com.example.mapper;

import com.example.config.oauth.OAuthUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author yuheng.lin@hand-china.com
 */
@Mapper
public interface OAuthUserMapper {
    @Insert("insert into oauth2_user(username, password, salt) values(#{oAuthUser.username},#{oAuthUser.password},#{oAuthUser.salt})")
    OAuthUser insert(OAuthUser oAuthUser);

    @Update("update oauth2_user set username=#{oAuthUser.username}, password=#{oAuthUser.password}, salt=#{oAuthUser.salt} where id=#{oAuthUser.id}")
    OAuthUser update(OAuthUser oAuthUser);

    @Delete("delete from oauth2_user where id=#{id}")
    void delete(@Param("id") Long id);

    @Select("select id, username, password, salt from oauth2_user where id=#{id}")
    OAuthUser findOne(@Param("id") Long id);

    @Select("select id, username, password, salt from oauth2_user")
    List<OAuthUser> findAll();

    @Select("select id, username, password, salt from oauth2_user where username=#{username}")
    OAuthUser findByUsername(@Param("username") String username);
}
