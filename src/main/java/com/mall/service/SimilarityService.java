package com.mall.service;

import com.mall.dataobject.UserSimilarityDTO;

import java.util.List;

public interface SimilarityService {
    boolean saveOrUpdateUserSimilarity(UserSimilarityDTO userSimilarityDTO);

    boolean saveUserSimilarity(UserSimilarityDTO userSimilarityDTO);

    boolean updateUserSimilarity(UserSimilarityDTO userSimilarityDTO);

    boolean isExistsUserSimilarity(UserSimilarityDTO userSimilarityDTO);

    List<UserSimilarityDTO> listUserSimilarityByUId(Integer userId);
}
