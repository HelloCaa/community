package com.sny.community.mapper;

import com.sny.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified, avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified}, #{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccount(@Param("accountId") String accountId);

    @Update("update user set GMT_modified=#{gmtModified}, avatar_url=#{avatarUrl}, NAME=#{name}, token=#{token} where ACCOUNT_ID=#{accountId}")
    void update(User dbUser);
}
