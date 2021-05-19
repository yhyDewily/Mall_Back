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

    @Test
    void register() {
        User user = new User();
        user.setUsername("eevee");
        user.setPassword("eevee1");
        user.setSex(0);
        user.setEmail("eevee@gmail.com");
        user.setPhone("18817452369");
        user.setQuestion("问题一");
        user.setAnswer("答案二");
        user.setRole(0);
        userService.register(user);
    }
}