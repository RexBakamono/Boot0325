package com.rex.boot.service.impl;

import com.rex.boot.bean.User;
import com.rex.boot.mapper.UserMapper;
import com.rex.boot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public List<User> queryFirstAll() {
        return userMapper.queryFirstAll();
    }

    @Override
    public List<User> querySecondAll() {
        return userMapper.querySecondAll();
    }

    @Override
    public int deleteById(int id) {
        return userMapper.deleteById(id);
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    @Async("asyncServiceExecutor")
    public void test() {
        logger.info("start executeAsync");

        System.out.println("异步线程要做的事情");
        System.out.println("可以在这里执行批量插入等耗时的事情");

        logger.info("end executeAsync");

    }

    @Override
    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }

    @Override
    public String getUser(String name, String pass) {
        return userMapper.getUser(name, pass);
    }


}
