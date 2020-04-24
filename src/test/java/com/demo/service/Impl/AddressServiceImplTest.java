package com.demo.service.Impl;

import com.demo.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@RunWith(SpringRunner.class)
class AddressServiceImplTest {

    @Autowired
    AddressServiceImpl addressService;

    @Autowired
    AddressRepository repository;

    @Test
    void getUserAddressList() {
        System.out.println(repository.findAllByUserId(1));
    }
}