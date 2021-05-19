package com.mall.repository;

import com.mall.dataobject.Appraise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppraiseRepository extends JpaRepository<Appraise, Integer> {

    @Query(value = "select * from mall.appraise where product_id = ?1", nativeQuery = true)
    Page<Appraise> findByProductId(Integer productId, Pageable pageable);
}
