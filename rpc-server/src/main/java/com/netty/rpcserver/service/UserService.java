package com.netty.rpcserver.service;

import com.netty.rpcserver.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
}
