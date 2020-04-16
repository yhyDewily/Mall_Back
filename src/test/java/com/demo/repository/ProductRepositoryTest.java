package com.demo.repository;

import com.demo.dataobject.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    @Test
    void findMaleProduct() {
        List<Product> list = repository.findProductBySex(100001);
    }
}