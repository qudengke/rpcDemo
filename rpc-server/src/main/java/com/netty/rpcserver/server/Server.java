package com.netty.rpcserver.server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface Server {
    void start() throws IOException;
    void stop();
    void register(Class service,Class serviceImp);
}
