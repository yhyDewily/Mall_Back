package com.demo.service.Impl;

import com.demo.dataobject.Product;
import com.demo.repository.ProductRepository;
import com.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;


    @Override
    public Product getProductInfo(Integer productId) {
        return repository.findByProductId(productId);
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }
}
