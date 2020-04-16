package com.demo.service;

import com.demo.dataobject.Product;

import java.util.List;

public interface ProductService {
    Product getProductInfo(Integer productId);

    List<Product> getAll();
}
