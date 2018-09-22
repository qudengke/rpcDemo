package com.netty.rpcserver;

import com.netty.rpcserver.server.Server;
import com.netty.rpcserver.server.impl.ServerImpl;
import com.netty.rpcserver.service.UserService;
import com.netty.rpcserver.service.impl.UserServiceImpl;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Server server=new ServerImpl();
        server.register(UserService.class, UserServiceImpl.class);
        server.start();
    }
}
