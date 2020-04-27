package com.mall.repository;

import com.mall.dataobject.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayInfoRepository extends JpaRepository<PayInfo, Integer> {
}
