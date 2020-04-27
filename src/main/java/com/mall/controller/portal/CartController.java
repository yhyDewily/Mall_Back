package com.mall.controller.portal;

import com.mall.common.Const;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.dataobject.Product;
import com.mall.dataobject.User;
import com.mall.service.Impl.CartServiceImpl;
import com.mall.service.Impl.ProductServiceImpl;
import com.mall.service.Impl.UserServiceImpl;
import com.mall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ProductServiceImpl productService;


    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<CartVo> list(Integer userId){
        User user = userService.getUserInfo(userId);
        if(user == null) return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        return cartService.list(userId);
    }

    @RequestMapping(value = "add.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<CartVo> add(int userId, Integer count, Integer productId){
        User user = userService.getUserInfo(userId);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.add(user.getId(),productId,count);
    }

    @RequestMapping(value = "update.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<CartVo>update(int userId, int productId, int quantity){
        User user = userService.getUserInfo(userId);
        Product product = productService.getProductInfo(productId);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if(product == null){
            return ServerResponse.createBySuccessMessage("商品不存在");
        }
        return cartService.update(userId, productId, quantity);
    }

    @RequestMapping(value = "delete_product.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<CartVo> deleteProduct(int userId,int productId){
        User user = userService.getUserInfo(userId);
        Product product = productService.getProductInfo(productId);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if(product == null){
            return ServerResponse.createBySuccessMessage("商品不存在");
        }
        return cartService.deleteProduct(user.getId(),productId);
    }

    @RequestMapping(value = "select_all.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<CartVo> selectAll(int userId){
        User user = userService.getUserInfo(userId);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelectAll(user.getId(), Const.Cart.CHECKED);
    }

    @RequestMapping(value = "un_select_all.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<CartVo> unSelectAll(int userId){
        User user = userService.getUserInfo(userId);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return cartService.selectOrUnSelectAll(user.getId(), Const.Cart.UN_CHECKED);
    }

    @RequestMapping("select.do")
    @ResponseBody
    @CrossOrigin
    public ServerResponse<CartVo> select(int userId,Integer productId){
        User user = userService.getUserInfo(userId);
        Product product = productService.getProductInfo(productId);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if(product == null){
            return ServerResponse.createBySuccessMessage("商品不存在");
        }
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.CHECKED);
    }

    @RequestMapping("un_select.do")
    @ResponseBody
    @CrossOrigin
    public ServerResponse<CartVo> unSelect(int userId,Integer productId){
        User user = userService.getUserInfo(userId);
        Product product = productService.getProductInfo(productId);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        if(product == null){
            return ServerResponse.createBySuccessMessage("商品不存在");
        }
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.UN_CHECKED);
    }
}
