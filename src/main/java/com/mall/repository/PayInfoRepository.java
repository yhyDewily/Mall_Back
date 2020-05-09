package com.mall.repository;

import com.mall.dataobject.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface PayInfoRepository extends JpaRepository<PayInfo, Integer> {

    PayInfo findByOrderNo(Long orderNo);
}
