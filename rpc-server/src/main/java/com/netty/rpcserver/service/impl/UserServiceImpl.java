package com.netty.rpcserver.service.impl;

import com.netty.rpcserver.model.User;
import com.netty.rpcserver.service.UserService;

import java.util.Arrays;
import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public List<User> getUsers() {
        User user1=new User(1,"小明");
        User user2=new User(2,"小红");
        List<User> users = Arrays.asList(user1, user2);
        return users;
    }
}
