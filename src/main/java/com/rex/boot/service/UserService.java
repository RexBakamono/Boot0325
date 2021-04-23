package com.rex.boot.service;

import com.rex.boot.bean.User;

import java.util.List;

public interface UserService {
    List<User> queryFirstAll();
    List<User> querySecondAll();

    int deleteById(int id);

    int insertUser(User user);


    void test();

    User findUserById(int id);

    String getUser(String name, String pass);
}
