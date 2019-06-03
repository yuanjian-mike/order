package com.yj.order.service.impl;

import com.yj.order.dao.UserDao;
import com.yj.order.model.User;
import com.yj.order.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }
}
