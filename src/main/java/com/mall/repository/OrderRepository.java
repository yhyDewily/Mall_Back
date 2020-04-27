package com.mall.repository;

import com.mall.dataobject.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByOrderNoAndUserId(Long orderNo, Integer userId);

    Order findByOrderNo(Long orderNo);
}
