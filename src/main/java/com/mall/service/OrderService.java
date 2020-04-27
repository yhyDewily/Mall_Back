package com.mall.service;

import com.mall.common.ServerResponse;

import java.util.Map;

public interface OrderService {
    ServerResponse createOrder(Integer userId, Integer addressId);

    ServerResponse pay(Integer userId, Long orderNo);

    ServerResponse aliCallback(Map<String, String> params);

    ServerResponse getOrderCartProduct(Integer userId);

    ServerResponse getOrderDetail(Integer userId, Long orderNo);

    ServerResponse getOrderList(Integer id, int pageNum, int pageSize);
}
