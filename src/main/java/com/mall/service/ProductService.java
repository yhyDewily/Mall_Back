package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.dataobject.Product;
import com.mall.vo.ProductDetailVo;
import com.mall.vo.ProductVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ServerResponse saveOrUpdateProduct(ProductVo productVo);

    ProductVo getProductInfo(Integer productId);

    List<Product> getAll();

    ServerResponse setSaleStatus(Integer productId, Integer status);

    ServerResponse manageProductDetail(Integer productId);

    ServerResponse<Page> getProductList(int currentPage, int pageSize);

    ServerResponse searchProduct(String productName, int pageNum, int pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    ServerResponse getProductByKeywordCategory(String keyword, int pageNum, int pageSize);

    ServerResponse getProductByCategory(Integer keyword, int pageNum, int pageSize);

    Product VoToProduct(ProductVo productVo);

    ServerResponse getHotProduct();

    List<Product> getMostHits();

    ServerResponse addProductHit(Integer productId);

    ServerResponse addCategoryHit(Integer userId, Integer productId);

    ServerResponse checkPurchase(Integer userId, Integer productId);
}
