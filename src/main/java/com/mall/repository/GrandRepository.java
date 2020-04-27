package com.mall.repository;

import com.mall.dataobject.Grand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GrandRepository extends JpaRepository<Grand, Integer> {

    @Query(value = "select id from mall.grand where name=?", nativeQuery = true)
    int findIdByName(String name);
}
