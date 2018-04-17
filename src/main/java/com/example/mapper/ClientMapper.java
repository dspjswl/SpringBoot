package com.example.mapper;

import com.example.dto.OAuthClient;
import com.example.util.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author yuheng.lin@hand-china.com
 */
@Mapper
public interface ClientMapper {
    @Insert("insert into oauth2_client(client_name, client_id, client_secret) values(#{client.clientName},#{client.clientId},#{client.clientSecret})")
    OAuthClient insertClient(OAuthClient client);

    @Update("update oauth2_client set client_name=#{client.clientName}, client_id=#{client.clientId}, client_secret=#{client.clientSecret} where id=#{client.id}")
    OAuthClient updateClient(OAuthClient client);

    @Delete("delete from oauth2_client where id=#{id}")
    void deleteClient(@Param("id") Long id);

    @Select("select * from oauth2_client where id=#{id}")
    OAuthClient findOne(@Param("id") Long id);

    @Select("select * from oauth2_client")
    List<OAuthClient> findAll();

    //@Select("select * from oauth2_client where client_id=#{clientId}")
    OAuthClient findByClientId(@Param("clientId") String clientId);

    @Select("select * from oauth2_client where client_secret=#{client.clientSecret}")
    OAuthClient findByClientSecret(@Param("clientSecret") String clientSecret);
}
