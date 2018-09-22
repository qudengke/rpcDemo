package com.netty.rpcserver.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private Integer id;
    private String name;
    private String password;

}
