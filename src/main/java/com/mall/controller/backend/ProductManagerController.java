package com.mall.controller.backend;

import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.dataobject.Product;
import com.mall.dataobject.User;
import com.mall.service.Impl.CategoryServiceImpl;
import com.mall.service.Impl.ProductServiceImpl;
import com.mall.service.Impl.UserServiceImpl;
import com.mall.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manage/product/")
public class ProductManagerController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @RequestMapping(value = "save.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse productSave(Integer userId, ProductVo productVo){
        User user = userService.getUserInfo(userId);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

        }
        if(userService.checkAdminRole(user).isSuccess()){
            //填充我们增加产品的业务逻辑
            return productService.saveOrUpdateProduct(productVo);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping(value = "set_sale_status.do",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse setSaleStatus(Integer userId, Integer productId,Integer status){
        User user = userService.getUserInfo(userId);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

        }
        if(userService.checkAdminRole(user).isSuccess()){
            return productService.setSaleStatus(productId,status);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping(value = "detail.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<ProductVo> getDetail(Integer userId, Integer productId){
        User user = userService.getUserInfo(userId);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

        }
        if(userService.checkAdminRole(user).isSuccess()){
            //填充业务
            return ServerResponse.createBySuccess(productService.getProductInfo(productId));

        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse getList(Integer userId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = userService.getUserInfo(userId);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //填充业务
            return productService.getProductList(pageNum - 1,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping(value = "search.do",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse productSearch(Integer userId, String productName, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "4") int pageSize){
        User user = userService.getUserInfo(userId);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //填充业务
            return productService.getProductByKeywordCategory(productName,pageNum,pageSize);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping(value = "id_search.do",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse productIdSearch(Integer userId, Integer productId){
        User user = userService.getUserInfo(userId);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //填充业务
            Product product = productService.getProductById(productId);
            if (product == null) return ServerResponse.createByErrorMessage("没有找到商品");
            else return ServerResponse.createBySuccess(product);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping(value = "category_search.do",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse productCategorySearch(Integer userId, Integer categoryId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize ){
        User user = userService.getUserInfo(userId);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //填充业务
            return productService.getProductByCategory(categoryId, pageNum, pageSize);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping(value = "grand_search.do",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse productGrandSearch(Integer userId, Integer grandId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize ){
        User user = userService.getUserInfo(userId);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");
        }
        if(userService.checkAdminRole(user).isSuccess()){
            //填充业务
            return productService.getProductByGrandId(grandId, pageNum, pageSize);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }
}
