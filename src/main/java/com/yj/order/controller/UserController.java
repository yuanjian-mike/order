package com.yj.order.controller;

import com.yj.order.model.User;
import com.yj.order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/insert")
    public void insert() {
        User user = new User();
        user.setId(UUID.randomUUID().toString().replaceAll("-",""));
        user.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
        user.setOrderId(1L);
        user.setUserId(user.getId());
        user.setUserName("张三");
        user.setPassword("123");
        user.setNickName("胖的惊动党中央");
        userService.insert(user);
    }
}
