package com.netty.rpcclient;

import com.netty.rpcclient.service.ClientService;
import com.netty.rpcclient.service.impl.ClientServiceImpl;
import com.netty.rpcserver.model.User;
import com.netty.rpcserver.service.UserService;

import java.net.InetSocketAddress;
import java.util.List;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        ClientService clientService=new ClientServiceImpl();
        UserService userSevice = clientService.getRemotProxy(UserService.class, new InetSocketAddress("127.0.0.1",8888));
        List<User> users = userSevice.getUsers();
        users.stream().forEach(user -> System.out.println(user.getName()));
        System.out.println(1);
    }
}
