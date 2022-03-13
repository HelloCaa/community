package com.sny.community.service;

import com.sny.community.mapper.UserMapper;
import com.sny.community.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;


    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccount(user.getAccountId());
        if(dbUser == null){
            //将用户数据插入到我们的数据库
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            //将我们的数据库中的用户数据进行更新
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }
    }
}
