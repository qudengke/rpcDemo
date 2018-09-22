package com.netty.rpcserver.server.impl;

import com.netty.rpcserver.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.*;

public class ServerImpl implements Server {

    private static HashMap<String,Class> registerMap=new HashMap<>();
    private static ExecutorService executorService= newFixedThreadPool(10);
    private static boolean isRuning=false;

    @Override
    public void start() throws IOException {
        System.out.println("server start !!!");
        ServerSocket serverSocket=new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8888));
        isRuning=true;
        while (true) {
            Socket accept = serverSocket.accept();
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    ObjectInputStream inputStream = null;
                    ObjectOutputStream opt = null;
                    try {
                        inputStream = new ObjectInputStream(accept.getInputStream());
                        String serviceName = inputStream.readUTF();
                        String method = inputStream.readUTF();
                        Class[] types = (Class[]) inputStream.readObject();
                        Object[] args = (Object[]) inputStream.readObject();
                        Class serviceImpl = registerMap.get(serviceName);
                        Method method1 = serviceImpl.getMethod(method, types);
                        Object obj = method1.invoke(serviceImpl.newInstance(), args);
                        opt = new ObjectOutputStream(accept.getOutputStream());
                        opt.writeObject(obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (opt != null) {
                                opt.close();
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void stop() {
        isRuning=false;
        executorService.shutdown();

    }

    @Override
    public void register(Class service,Class serviceImp) {
        registerMap.put(service.getName(),serviceImp);
    }
}
