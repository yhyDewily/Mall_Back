package com.demo.service.Impl;

import com.demo.dataobject.Product;
import com.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProductServiceImplTest {

    @Autowired
    ProductRepository repository;

    @Test
    void getProductInfo() {
        Product product = repository.findByProductId(1);
        System.out.println(product);
    }

    @Test
    void findAllTest() {
        for (Product product : repository.findAllProduct())
            System.out.println(product);
    }
}