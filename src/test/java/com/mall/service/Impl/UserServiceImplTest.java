package com.mall.service.Impl;

import com.mall.dataobject.User;
import com.mall.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    void login() {

    }

    @Test
    void getUserInfoTest() {
        User user = userService.getUserInfo(1);
        System.out.println(user);
    }
}