package com.demo.controller.backend;

import com.demo.common.Const;
import com.demo.common.ServerResponse;
import com.demo.dataobject.User;
import com.demo.service.Impl.UserServiceImpl;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager/user/")
public class UserManager {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = userService.login(username, password);
        if (response.isSuccess()) {
            User user = response.getData();
            //说明登录的是管理员
            if (user.getRole() == Const.Role.ROLE_ADMIN) {
                session.setAttribute(Const.CURRENT_USER, user);
                return response;
            } else return ServerResponse.createByErrorMessage("不是管理员，无法登录");
        }
        return response;
    }
}
