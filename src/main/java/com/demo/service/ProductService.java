package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.dataobject.Product;
import com.demo.vo.ProductDetailVo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    Product getProductInfo(Integer productId);

    List<Product> getAll();

    ServerResponse setSaleStatus(Integer productId, Integer status);

    ServerResponse manageProductDetail(Integer productId);

    ServerResponse<Page> getProductList(int currentPage, int pageSize);

    ServerResponse searchProduct(String productName, int pageNum, int pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);
}
