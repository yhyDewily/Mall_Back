package com.demo.service;

import com.demo.common.ServerResponse;
import com.demo.vo.CartProductVo;
import com.demo.vo.CartVo;
import org.springframework.stereotype.Service;


public interface CartService {
    ServerResponse<CartVo> list(Integer userId);

    CartVo getCartVoLimit(Integer userId);

    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    ServerResponse<CartVo> deleteProduct(Integer userId, Integer productId);

    ServerResponse<CartVo> selectOrUnSelectAll(Integer userId, int checked);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, int checked);

    ServerResponse<CartVo> update(int userId, int productId, int quantity);
}
