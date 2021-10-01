package com.handsomedong.dynamic.datasource.demo.controller;

import com.handsomedong.dynamic.datasource.demo.entity.User;
import com.handsomedong.dynamic.datasource.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by HandsomeDong on 2021/9/29 23:47
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @GetMapping("/users_handsomedong")
    public List<User> getUserListFromHandsomeDong() {
        return userService.getUserListFromHandsomeDong();
    }


    @GetMapping("/sharding_user")
    public List<User> getShardingUser(@RequestParam Integer id) {
        return userService.getShardingUser(id);
    }
}
