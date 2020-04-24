package com.demo.service.Impl;

import com.demo.common.Const;
import com.demo.common.ServerResponse;
import com.demo.dataobject.Product;
import com.demo.dataobject.User;
import com.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

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