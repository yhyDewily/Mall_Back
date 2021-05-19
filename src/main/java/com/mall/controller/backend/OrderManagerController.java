package com.mall.controller.backend;

import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.dataobject.User;
import com.mall.service.Impl.OrderServiceImpl;
import com.mall.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manage/order/")
public class OrderManagerController {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    UserServiceImpl userService;

    @RequestMapping(value = "list.do",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse getAllOrder(Integer userId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {
        User user = userService.getUserInfo(userId);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //是管理员
            //增加我们处理分类的逻辑
            return orderService.getAllOrderList(pageNum, pageSize);

        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "orderNo_search.do",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse OrderNoSearch(Integer userId, String keyword, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {
        User user = userService.getUserInfo(userId);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //是管理员
            //增加我们处理分类的逻辑
            return orderService.getOrderListByOrderNo(keyword, pageNum, pageSize);

        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "detail.do",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse OrderDetail(Integer userId, Long orderNo) {
        User user = userService.getUserInfo(userId);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //是管理员
            //增加我们处理分类的逻辑
            return orderService.getOrderDetail(userId, orderNo);

        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping(value = "send_product.do",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse SendProduct(Integer userId, Long orderNo) {
        User user = userService.getUserInfo(userId);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //是管理员
            //增加我们处理分类的逻辑
            return orderService.sendOrder(orderNo);

        } else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }
}
