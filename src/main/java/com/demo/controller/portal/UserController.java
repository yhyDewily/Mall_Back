package com.demo.controller.portal;

import com.demo.common.Const;
import com.demo.common.ResponseCode;
import com.demo.common.ServerResponse;
import com.demo.dataobject.User;
import com.demo.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<User> login(String username, String password, HttpServletRequest request) {
        ServerResponse<User> response = userService.login(username, password);
        if (response.isSuccess()) request.getSession().setAttribute(Const.CURRENT_USER, response.getData());
        return response;
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<User> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<String> register(User user) {
        return userService.register(user);

    }

    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<String> checkValid(String str, String type) {
        return userService.checkValid(str, type);
    }

    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<User> getUserInfo(Integer userId) {
        User user = userService.getUserInfo(userId);
        if (user != null) {
            user.setPassword("");
            return ServerResponse.createBySuccess(user);
        }

        return ServerResponse.createByErrorMessage("用户不存在");
    }

//    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
//    @ResponseBody
//    @CrossOrigin
//    public ServerResponse<User> getUserInfo(HttpServletRequest request) {
//        User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
//        if(user != null) return ServerResponse.createBySuccess(user);
//        return ServerResponse.createByErrorMessage("用户不存在");
//    }


    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<String> forgetGetQuestion(String username) {
        return userService.selectQuestion(username);

    }

    @RequestMapping(value = "forget_check_question.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return userService.checkAnswer(username, question, answer);

    }

    @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken) {
        return userService.forgetRestPassword(username, passwordNew, forgetToken);

    }

    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<String> resetPassWord(HttpSession session, String passwordOld, String passwordNew) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) return ServerResponse.createByErrorMessage("用户未登录");
        return userService.restPassword(passwordOld, passwordNew, user);
    }

    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<User> updateInformation(HttpSession session, User user) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) return ServerResponse.createByErrorMessage("用户未登录");
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = userService.update_information(user);
        if (response.isSuccess()) session.setAttribute(Const.CURRENT_USER, response.getData());

        return response;
    }

    @RequestMapping(value = "get_information.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<User> get_information(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null)
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录，需要强制登录，status=10");
        return userService.getInformation(currentUser.getId());
    }
}
