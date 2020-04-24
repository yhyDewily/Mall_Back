package com.demo.repository;

import com.demo.dataobject.Product;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    @Test
    void testPage() {
        Pageable pageable = PageRequest.of(0,3);
        Page<Product> page = repository.findAll(pageable);

//        Page<Product> data = repository.findAllProduct(pageable);
//        System.out.println("总条数" + data.getTotalElements());
//        System.out.println("总页数" + data.getTotalPages());
        for (Product product : page) {
            System.out.println(product);
        }
    }

    @Test
    void testFind(){
//        List<Product> products = repository.findProductBySex(100001);
//        for (Product product : products) {
//            System.out.println(product);
//        }
    }
}