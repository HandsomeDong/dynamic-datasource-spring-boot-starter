package com.handsomedong.dynamic.datasource.demo.service;

import com.handsomedong.dynamic.datasource.demo.entity.User;
import com.handsomedong.dynamic.datasource.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by HandsomeDong on 2021/9/30 0:03
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    public List<User> getUserListFromHandsomeDong() {
        return userMapper.getUserListFromHandsomeDong();
    }
}
