package com.mall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.common.collect.Maps;
import com.mall.common.Const;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.dataobject.Order;
import com.mall.dataobject.OrderItem;
import com.mall.dataobject.Product;
import com.mall.dataobject.User;
import com.mall.service.Impl.CartServiceImpl;
import com.mall.service.Impl.OrderServiceImpl;
import com.mall.service.Impl.ProductServiceImpl;
import com.mall.service.Impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order/")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    CartServiceImpl cartService;

    @RequestMapping("create.do")
    @ResponseBody
    @CrossOrigin
    public ServerResponse create(Integer userId, Integer addressId){
        User user = userService.getUserInfo(userId);
        if(user == null) return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        return orderService.createOrder(userId,addressId);
    }

    @RequestMapping("cancel.do")
    @ResponseBody
    @CrossOrigin
    public ServerResponse cancel(Integer userId, Long orderNo){
        User user = userService.getUserInfo(userId);
        if(user == null) return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        return orderService.cancel(userId, orderNo);
    }


    @RequestMapping(value = "pay.do",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse pay(Integer userId, Long orderNo){
        User user = userService.getUserInfo(userId);
        if(user == null) return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        return orderService.pay(userId, orderNo);
    }

    @RequestMapping("get_order_cart_product.do")
    @ResponseBody
    @CrossOrigin
    public ServerResponse getOrderCartProduct(Integer userId){
        User user = userService.getUserInfo(userId);
        if(user == null) return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        return orderService.getOrderCartProduct(user.getId());
    }

    @RequestMapping("detail.do")
    @ResponseBody
    @CrossOrigin
    public ServerResponse detail(Integer userId,Long orderNo){
        User user = userService.getUserInfo(userId);
        if(user == null) return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        return orderService.getOrderDetail(userId,orderNo);
    }

    @RequestMapping("list.do")
    @ResponseBody
    @CrossOrigin
    public ServerResponse list(Integer userId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5") int pageSize){
        User user = userService.getUserInfo(userId);
        if(user == null) return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        return orderService.getOrderList(user.getId(),pageNum,pageSize);
    }


    @RequestMapping(value = "alipay_callback.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public Object alipayCallback(HttpServletRequest request){
        Map<String, String> params = Maps.newHashMap();
        Map requestParams =  request.getParameterMap();
        for(Iterator iterator = requestParams.keySet().iterator();iterator.hasNext();){
            String name = (String) iterator.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for(int i=0;i<values.length;i++){
                valueStr =  (i == values.length - 1)?valueStr + values[i]:valueStr + values[i]+",";
            }
            params.put(name, valueStr);
        }
        logger.info("支付宝回调,sign:{}, trade_status:{}, 参数:{}",params.get("sign"),params.get("trade_status"),params.toString());

        //验证回调正确性，避免重复通知
        params.remove("sign_type");
        String public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkI9jeRSQSdxigA/STrtYXL7wMrWVKat3gkAfO+geWU394+pZRANRCtcCuhyxe0CrYyWz7oCrHrZcEeuJ3wpZcVCtNLiH7yLlVcd1z3VDcG9EWwh3RtiMAgT6S60ssnxbweRn4Li+7YkLY7Gvi+7wF8UNJk0PyrP+fMlbkbTE3XExphmApbX57puLBWsMVLpnLRhvfCKZAvTTkelVi0pd2L38htN3GIoGnJ6HHQ6+J8t0xMELug3Hz2Ga32jPytoI2nBkIj987TIaftQK61TfAErhJa/CB7XzlQZzyXJVMFXJlC0V+iTLrVcl4fc5whToh6SS05MQ4zq8u0WCT2fAwQIDAQAB";
        String sign_type = "RSA2";
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, public_key, "utf-8", sign_type);
            if(!alipayRSACheckedV2){
                //业务逻辑
                return ServerResponse.createByErrorMessage("非法请求，验证不通过");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        ServerResponse serverResponse = orderService.aliCallback(params);
        if(serverResponse.isSuccess()){
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;
    }

    @RequestMapping(value = "query_order_pay.do",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<Boolean> queryOrderPay(Integer userId, Long orderNo){
        User user = userService.getUserInfo(userId);
        if(user == null) return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        ServerResponse serverResponse =  orderService.queryOrderPayStatus(userId, orderNo);
        if (serverResponse.isSuccess()) return ServerResponse.createBySuccess(true);
        return ServerResponse.createBySuccess(false);
    }

    @RequestMapping(value = "confirm_shipping.do",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse confirmShipping(Integer userId, Long orderNo) {
        User user = userService.getUserInfo(userId);
        if(user == null) return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        return orderService.confirmShipping(userId, orderNo);
    }

}
