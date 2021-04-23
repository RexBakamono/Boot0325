package com.rex.boot.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.rex.boot.bean.User;
import com.rex.boot.jwt.JwtToken;
import com.rex.boot.jwt.JwtUtil;
import com.rex.boot.service.UserService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/user")
@ResponseBody
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/queryFirstAll")
    public String queryFirstAll() {
        return userService.queryFirstAll().toString();
    }

    @RequestMapping("/querySecondAll")
    public String querySecondAll() {
        return userService.querySecondAll().toString();
    }


    @RequestMapping("/deleteById")
    public int deleteById(int id) {
        return userService.deleteById(id);
    }

    @RequestMapping("/insertUser")
    public int insertUser(String name, String pass) {
        User user = new User();
        user.setName(name);
        user.setPass(pass);
        userService.insertUser(user);
        return user.getId();
    }

    @JwtToken
    @GetMapping("/findUserById")
    public User findUserById(int id) {
        return userService.findUserById(id);
    }

    /**
     * @param
     * @return $return
     * @author rex
     * @description //TODO 测试线程池
     * @date
     */
    @RequestMapping("/async")
    public void async() {
        userService.test();
    }

    /**
     * 登录并获取token
     *
     * @param name
     * @param pass
     * @return
     */
    @PostMapping("/login")
    public Object login(String name, String pass) {
        JSONObject jsonObject = new JSONObject();
        // 检验用户是否存在(为了简单，这里假设用户存在，并制造一个uuid假设为用户id)
        String userId = userService.getUser(name, pass);
        if (StringUtils.isEmpty(userId)) {
            jsonObject.put("code", -1);
            jsonObject.put("error", "用户名或密码错误");
        } else {
            // 生成签名
            String token = JwtUtil.sign(userId);
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("userId", userId);
            userInfo.put("userName", name);
            userInfo.put("passWord", pass);
            jsonObject.put("token", token);
            jsonObject.put("user", userInfo);
        }
        return jsonObject;
    }


}
