package com.mall.repository;

import com.mall.dataobject.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findByOrderNoAndUserId(Long orderNo, Integer userId);

    Order findByOrderNo(Long orderNo);

    List<Order> findByUserId(Integer userId);

    Page<Order> findByUserId(Integer userId, Pageable pageable);

    @Query(value = "select * from mall.mall_order where order_no like CONCAT('%',?1,'%')  ", nativeQuery = true)
    Page<Order> findByOrderNoKeyword(String keyword, Pageable pageable);
}
