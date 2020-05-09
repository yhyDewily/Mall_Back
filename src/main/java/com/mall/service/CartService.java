package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.vo.CartVo;


public interface CartService {
    ServerResponse<CartVo> list(Integer userId);

    CartVo getCartVoLimit(Integer userId);

    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count, String size);

    ServerResponse<CartVo> deleteProduct(Integer userId, Integer productId);

    ServerResponse<CartVo> selectOrUnSelectAll(Integer userId, int checked);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, int checked);

    ServerResponse<CartVo> update(int userId, int productId, int quantity);
}
