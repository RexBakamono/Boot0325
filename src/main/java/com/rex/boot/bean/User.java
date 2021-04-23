package com.rex.boot.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private int id;

    private String name;

    private String pass;

}