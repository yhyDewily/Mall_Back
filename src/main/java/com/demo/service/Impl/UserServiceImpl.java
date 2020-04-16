package com.demo.service.Impl;

import com.demo.common.Const;
import com.demo.common.ServerResponse;
import com.demo.common.TokenCache;
import com.demo.dataobject.User;
import com.demo.repository.UserRepository;
import com.demo.service.UserService;
import com.demo.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = repository.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题答案错误");
    }

    @Override
    public int updateAll(User user) {
        try {
            repository.save(user);
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    @Override
    public ServerResponse<User> update_information(User user) {
        int resultCount = repository.checkEmailByUserId(user.getEmail(), user.getId());
        if (resultCount > 0) return ServerResponse.createByErrorMessage("email已存在");

        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = this.updateAll(user);
        if (updateCount > 0) return ServerResponse.createBySuccess("更新个人信息成功", updateUser);
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }


    @Override
    public ServerResponse<User> getInformation(Integer id) {
        User user = repository.findByUserId(id);
        if (user == null) return ServerResponse.createByErrorMessage("找不到当前用户");
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = repository.checkUsername(username);
        if (resultCount == 0) return ServerResponse.createByErrorMessage("用户名不存在");

        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = repository.findByUsernameAndPassword(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);
    }

    @Override
    public User getUserInfo(Integer userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public ServerResponse<String> register(User user) {
        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        try {
            repository.save(user);
        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServerResponse checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            if (Const.USERNAME.equals(type)) {
                int resultCount = repository.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户已存在");
                }
            }
            if (Const.EMAIL.equals(type)) {
                int resultCount = repository.checkEmail(str);
                if (resultCount > 0) return ServerResponse.createByErrorMessage("email已存在");
            }
        } else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<String> selectQuestion(String username) {
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String question = repository.findQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createBySuccess(question);
        }

        return ServerResponse.createByErrorMessage("找回密码的问题不存在");
    }

    @Override
    public ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) return ServerResponse.createByErrorMessage("参数错误,需要传递Token");
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) return ServerResponse.createByErrorMessage("用户不存在");

        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) return ServerResponse.createByErrorMessage("token无效或过期");

        if (StringUtils.equals(forgetToken, token)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = repository.updatePasswordByUsername(username, md5Password);

            if (rowCount > 0) {
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        } else return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    @Override
    public ServerResponse<String> restPassword(String passwordOld, String passwordNew, User user) {
        int resultCount = repository.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (resultCount == 0) return ServerResponse.createByErrorMessage("旧密码错误");


        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = this.updateAll(user);
        if (updateCount > 0) return ServerResponse.createBySuccessMessage("密码更新成功");

        return ServerResponse.createByErrorMessage("密码更新失败");
    }

    @Override
    public ServerResponse<Object> checkAdminRole(User user) {
        if (user != null && user.getRole() == Const.Role.ROLE_ADMIN) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}
