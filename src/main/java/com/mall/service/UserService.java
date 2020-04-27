package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.dataobject.User;

public interface UserService {
    ServerResponse<String> checkAnswer(String username, String question, String answer);

    int updateAll(User user);

    ServerResponse<User> update_information(User user);

    ServerResponse<User> getInformation(Integer id);

    ServerResponse<User> login(String username, String password);

    User getUserInfo(Integer userId);

    ServerResponse<String> register(User user);

    ServerResponse checkValid(String str, String type);

    ServerResponse<String> selectQuestion(String username);

    ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<String> restPassword(String passwordOld, String passwordNew, User user);

    ServerResponse<Object> checkAdminRole(User user);
}
