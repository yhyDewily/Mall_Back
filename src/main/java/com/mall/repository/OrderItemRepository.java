package com.mall.repository;

import com.mall.dataobject.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem>findAllByOrderNoAndUserId(Long orderNo, Integer userId);

    List<OrderItem> findByUserId(Integer userId);

    List<OrderItem> findByOrderNo(Long orderNo);

}
