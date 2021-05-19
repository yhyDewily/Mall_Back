package com.mall.controller.portal;

import com.mall.common.Const;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.dataobject.Product;
import com.mall.dataobject.User;
import com.mall.dataobject.UserActiveDTO;
import com.mall.dataobject.UserSimilarityDTO;
import com.mall.service.Impl.ActiveServiceImpl;
import com.mall.service.Impl.ProductServiceImpl;
import com.mall.service.Impl.SimilarityServiceImpl;
import com.mall.service.Impl.UserServiceImpl;
import com.mall.util.RecommendUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    SimilarityServiceImpl similarityService;

    @Autowired
    ActiveServiceImpl activeService;

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

    @RequestMapping(value = "check_identify.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse checkIdentify(String mobile, String identifyCode) {
        return userService.checkIdentify(mobile, identifyCode);
    }

    @RequestMapping(value = "sendSms.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse sendSms(String mobile) {
        if(userService.checkPhone(mobile)>0) return ServerResponse.createByErrorMessage("手机号已存在");
        return userService.sendSms(mobile);
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
    @RequestMapping(value = "check_phone.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<String> checkPhone(String mobile) {
        if(userService.checkPhone(mobile)>0) return ServerResponse.createByErrorMessage("手机号已存在");
        return ServerResponse.createBySuccessMessage("手机号未被使用");
    }

    @RequestMapping(value = "check_user_phone.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse checkUserByPhone(String mobile) {
        if(userService.checkPhone(mobile) > 0) {
            return ServerResponse.createBySuccess(userService.getUserIdByPhone(mobile));
        }
        return ServerResponse.createByErrorMessage("手机号未绑定");
    }

    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<String> forgetGetQuestion(Integer userId) {
        User user = userService.getUserInfo(userId);
        if(user == null) return ServerResponse.createByErrorMessage("用户不存在");
        return userService.selectQuestion(userId);

    }

    @RequestMapping(value = "forget_check_question.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse forgetCheckAnswer(Integer userId, String question, String answer) {
        return userService.checkAnswer(userId, question, answer);

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
    public ServerResponse<String> resetPassWord(Integer userId, String passwordNew) {
        User user = userService.getUserInfo(userId);
        if(user == null) return ServerResponse.createByErrorMessage("用户不存在");
        return userService.restPassword(userId,passwordNew);
    }

    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<User> updateInformation(User user) {
        User former_user = userService.getUserInfo(user.getId());
        if (former_user == null) return ServerResponse.createBySuccessMessage("用户不存在");
        return userService.update_information(user);
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

    @RequestMapping(value = "get_similarity.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse getSimilarity(Integer userId) {
        User user = userService.getUserInfo(userId);
        if(user == null) {
            return ServerResponse.createBySuccess(productService.getHotProduct());
        }
        if(userService.checkSimilarity(userId) >0) {
            return ServerResponse.createBySuccess(userService.getSimilarity(userId));
        }
        return ServerResponse.createBySuccess("暂无点击行为，返回热门产品",productService.getHotProduct());
    }


}
