package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.dataobject.Product;
import com.mall.dataobject.User;

import java.util.List;

public interface UserService {
    ServerResponse<String> checkAnswer(Integer userId, String question, String answer);

    int updateAll(User user);

    ServerResponse<User> update_information(User user);

    ServerResponse<User> getInformation(Integer id);

    ServerResponse<User> login(String username, String password);

    User getUserInfo(Integer userId);

    ServerResponse<String> register(User user);

    ServerResponse checkValid(String str, String type);

    ServerResponse<String> selectQuestion(Integer useId);

    ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<String> restPassword(Integer userId, String passwordNew);

    ServerResponse<Object> checkAdminRole(User user);

    List<Product> getSimilarity(Integer userId);
}
