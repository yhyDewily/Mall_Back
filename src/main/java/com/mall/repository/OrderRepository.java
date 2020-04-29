package com.mall.repository;

import com.mall.dataobject.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByOrderNoAndUserId(Long orderNo, Integer userId);

    Order findByOrderNo(Long orderNo);

    List<Order> findByUserId(Integer userId);

    List<Order> findByUserId(Integer userId, Pageable pageable);
}
