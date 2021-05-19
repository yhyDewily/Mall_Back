package com.mall.repository;

import com.mall.dataobject.UserActiveDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserActiveRepository extends JpaRepository<UserActiveDTO, Integer> {

    UserActiveDTO findByUserIdAndAndCategory2Id(Integer userId, Integer categoryId);

    int countUserActiveDTOByUserIdAndCategory2Id(Integer userId, Integer categoryId);

    @Query(value = "select hits from mall.shopping_active where user_id=?1 and category2_id=?2", nativeQuery = true)
    Long findHitsByUserIdAndCategory2Id(Integer userId, Integer categoryId);
}
