package com.yj.order.service.impl;

import com.yj.order.dao.UserDao;
import com.yj.order.model.User;
import com.yj.order.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(User user) {
        if(user == null) {
            user = new User();
            user.setId(UUID.randomUUID().toString().replaceAll("-",""));
            user.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
            user.setOrderId(1L);
            user.setUserId(user.getId());
            user.setUserName("张三");
            user.setPassword("123");
            user.setNickName("胖的惊动党中央");
        }
        userDao.insert(user);
    }
}
