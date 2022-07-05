package com.sny.community.service;

import com.sny.community.mapper.UserMapper;
import com.sny.community.model.User;
import com.sny.community.model.UserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;


    public void createOrUpdate(User user) {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() == 0){
            //将用户数据插入到我们的数据库
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            //将我们的数据库中的用户数据进行更新
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());

            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        }
    }

    public boolean queryUserByName(String userName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(userName);
        List<User> users = userMapper.selectByExample(userExample);
        return users.size() == 0;
    }

    public boolean queryUserById(String loginId) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(String.valueOf(loginId));
        List<User> users = userMapper.selectByExample(userExample);
        return users.size() == 0;
    }
}
