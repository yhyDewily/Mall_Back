package com.mall.controller.backend;

import com.mall.common.Const;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.dataobject.User;
import com.mall.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager/user/")
public class UserManager {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
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

//    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
//    @ResponseBody
//    @CrossOrigin
//    public ServerResponse logout(HttpSession session){
//        session.removeAttribute(Const.CURRENT_USER);
//        return ServerResponse.createBySuccess((User)session.getAttribute(Const.CURRENT_USER));
//    }

//    @RequestMapping(value = "login.do", method = RequestMethod.POST)
//    @ResponseBody
//    @CrossOrigin
//    public ServerResponse<User> login(String username, String password) {
//        ServerResponse<User> response = userService.login(username, password);
//        if (response.isSuccess()) {
//            User user = response.getData();
//            //说明登录的是管理员
//            if (user.getRole() == Const.Role.ROLE_ADMIN) {
//                return response;
//            } else return ServerResponse.createByErrorMessage("不是管理员，无法登录");
//        }
//        return response;
//    }

    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse getUsers(Integer userId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "4") int pageSize) {
        User user = userService.getUserInfo(userId);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //填充业务
            return userService.getUserList(pageNum, pageSize);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping(value = "add_admin.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse addAdmin(Integer userId, Integer anotherUser, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "4") int pageSize) {
        User user = userService.getUserInfo(userId);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //填充业务
            return userService.setAdmin(anotherUser);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


}
