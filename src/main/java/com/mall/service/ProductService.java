package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.dataobject.Product;
import com.mall.vo.ProductDetailVo;
import org.springframework.data.domain.Page;

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
