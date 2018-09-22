package com.netty.rpcclient.service;

import java.net.InetSocketAddress;

public interface ClientService {
    <T> T getRemotProxy(Class serviveName, InetSocketAddress address);
}
