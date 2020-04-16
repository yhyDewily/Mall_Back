package com.demo.repository;

import com.demo.dataobject.User;
import com.demo.service.Impl.UserServiceImpl;
import com.demo.util.MD5Util;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserRepositoryTest {

    @Autowired
    UserServiceImpl service;

    @Autowired
    UserRepository repository;

    @Test
    void findByUsername() {

    }

    @Test
    void findByUsernameAndPassword() {
        String username = "admin";
        String password = "admin";
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = repository.findByUsernameAndPassword(username, md5Password);
        System.out.println(user);
    }

    @Test
    void testFindByUsername() {
        int resultCount = repository.checkUsername("admin");
        if (resultCount > 0) System.out.println("用户存在");
        else System.out.println("用户不存在");
    }

    @Test
    void testFindByUsernameAndPassword() {
    }

    @Test
    void checkEmail() {
        int resultCount = repository.checkEmail("admin@happymmall.com");
        System.out.println(resultCount);
    }

    @Test
    void updatePasswordByUsername() {
        String password = "admin";
        String user = "admin";
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        int resultCount = repository.updatePasswordByUsername(user, md5Password);
        System.out.println(resultCount);
    }
}