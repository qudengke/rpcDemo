package com.netty.rpcclient.service.impl;

import com.netty.rpcclient.service.ClientService;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientServiceImpl implements ClientService {
    @Override
    public <T> T getRemotProxy(Class serviveName, InetSocketAddress address) {
        return (T)Proxy.newProxyInstance(serviveName.getClassLoader(), new Class[]{serviveName}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket=new Socket();
                ObjectOutputStream outputStream=null;
                ObjectInputStream inputStream=null;
                try {
                    socket.connect(address);
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeUTF(serviveName.getName());
                    outputStream.writeUTF(method.getName());
                    outputStream.writeObject(method.getParameterTypes());
                    outputStream.writeObject(args);
                    inputStream = new ObjectInputStream(socket.getInputStream());
                    return inputStream.readObject();
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }finally {
                    if (outputStream!=null){
                        outputStream.close();
                    }
                    if (inputStream!=null){
                        inputStream.close();
                    }
                }
            }
        });
    }
}
