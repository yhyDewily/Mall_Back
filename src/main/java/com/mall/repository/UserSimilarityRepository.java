package com.mall.repository;

import com.mall.dataobject.UserSimilarityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserSimilarityRepository extends JpaRepository<UserSimilarityDTO, Integer> {

    @Query(value = "select count(*) from mall.shopping_similarity where user_id=?1 and user_ref_id=?2 OR user_ref_id=?1 and user_ref_id=?2", nativeQuery = true)
    int countUserSimilarity(Integer userId, Integer userReId);

    @Query(value = "select count(*) from mall.shopping_similarity where user_id=?1 or user_ref_id=?1", nativeQuery = true)
    int countUserSimilarity(Integer userId);

    List<UserSimilarityDTO> findAllByUserIdOrUserReId(Integer userId, Integer useReId);

    List<UserSimilarityDTO> findAllByUserId(Integer userId);

    @Query(value = "select * from mall.shopping_similarity where user_id=?1 and user_ref_id=?2 OR user_ref_id=?1 and user_ref_id=?2 limit 1", nativeQuery = true)
    UserSimilarityDTO findByUserIdAndUserReId(Integer userId, Integer userReId);
}
