package com.mall.service.Impl;

import com.mall.dataobject.User;
import com.mall.dataobject.UserSimilarityDTO;
import com.mall.repository.UserRepository;
import com.mall.repository.UserSimilarityRepository;
import com.mall.service.SimilarityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimilarityServiceImpl implements SimilarityService {

    @Autowired
    UserSimilarityRepository similarityRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean saveOrUpdateUserSimilarity(UserSimilarityDTO userSimilarityDTO) {
        try {
            similarityRepository.save(userSimilarityDTO);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean saveUserSimilarity(UserSimilarityDTO userSimilarityDTO) {
        try {
            similarityRepository.save(userSimilarityDTO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateUserSimilarity(UserSimilarityDTO userSimilarityDTO) {
        UserSimilarityDTO similarityDTO = similarityRepository.findByUserIdAndUserReId(userSimilarityDTO.getUserId(), userSimilarityDTO.getUserReId());
        try {
            similarityDTO.setSimilarity(userSimilarityDTO.getSimilarity());
            similarityRepository.save(similarityDTO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isExistsUserSimilarity(UserSimilarityDTO userSimilarityDTO) {
        int count = similarityRepository.countUserSimilarity(userSimilarityDTO.getUserId(), userSimilarityDTO.getUserReId());
        return count > 0;
    }

    @Override
    public List<UserSimilarityDTO> listUserSimilarityByUId(Integer userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) return null;
        return similarityRepository.findAllByUserId(userId);
    }
}
