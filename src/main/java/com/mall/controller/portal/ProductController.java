package com.mall.controller.portal;

import com.mall.common.GrandUtil;
import com.mall.common.ServerResponse;
import com.mall.dataobject.Product;
import com.mall.dataobject.User;
import com.mall.service.Impl.ProductServiceImpl;
import com.mall.service.Impl.UserServiceImpl;
import com.mall.vo.ProductDetailVo;
import com.mall.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    UserServiceImpl userService;

    //获取产品信息
    @RequestMapping(value = "get_product_info.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<ProductVo> getProductInfo(Integer productId) {
        Product product = productService.getProductById(productId);
        if (product != null) return ServerResponse.createBySuccess(productService.getProductInfo(productId));
        return ServerResponse.createByErrorMessage("产品不存在");
    }

    //增加产品点击量
    @RequestMapping(value = "product_hits", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse hitProduct(Integer productId) {
        if(productId !=null) {
            return productService.addProductHit(productId);
        }
        return ServerResponse.createByErrorMessage("产品不存在");
    }

    //增加用户的类目点击量
    @RequestMapping(value = "category_hits", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse hitCategory(Integer userId, Integer productId) {
        if(productId !=null) {
            return productService.addCategoryHit(userId, productId);
        }
        return ServerResponse.createByErrorMessage("产品不存在");
    }

    //获取所有商品信息
    @RequestMapping(value = "get_all.do", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<List<Product>> getAll() {
        List<Product> list = productService.getAll();
        if (list == null) {
            return ServerResponse.createByErrorMessage("没找到");
        }
        return ServerResponse.createBySuccess(list);
    }

    //获取品牌商品列表
    @RequestMapping(value = "grand_list.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse grandList(String grandName, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "12") int pageSize) {
        if(GrandUtil.getGrandList().contains(grandName)) {
            return productService.getProductByGrand(grandName, pageNum, pageSize);
        }
        return ServerResponse.createByErrorMessage("产品不存在");
    }

    @RequestMapping(value = "category_list.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse categoryList(@RequestParam(value = "keyword",required = false)Integer keyword,
                               @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "12") int pageSize){
        return productService.getProductByCategory(keyword,pageNum,pageSize);
    }


    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse list(@RequestParam(value = "keyword",required = false)String keyword,
                                              @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize",defaultValue = "12") int pageSize){
        return productService.getProductByKeywordCategory(keyword,pageNum,pageSize);
    }

    @RequestMapping(value = "sexList.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse genderList(int categoryId,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "12") int pageSize) {
        return productService.getProductBySex(categoryId, pageNum, pageSize);
    }

    @RequestMapping(value = "hot_products.do", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public ServerResponse getHottest() {
        return productService.getHotProduct();
    }

    @RequestMapping(value = "get_most_hits", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public ServerResponse getMostHits() {
        return ServerResponse.createBySuccess(productService.getMostHits());
    }

    @RequestMapping(value = "get_remark.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse getRemark(Integer productId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = "20") int pageSize) {
        Product product = productService.getProductById(productId);
        if (product == null) return ServerResponse.createByErrorMessage("产品不存在");
        return productService.getRemark(productId, pageNum, pageSize);
    }

    @RequestMapping(value = "check_purchase.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse checkPurchase(Integer userId, Integer productId) {
        User user = userService.getUserInfo(userId);
        if(user == null) return ServerResponse.createByErrorMessage("用户不存在");
        Product product = productService.getProductById(productId);
        if (product == null) return ServerResponse.createByErrorMessage("产品不存在");
        return productService.checkPurchase(userId, productId);
    }

    @RequestMapping(value = "add_remark.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse addRemark(Integer userId, Integer productId, String remark, Integer rate) {
        User user = userService.getUserInfo(userId);
        if(user == null) return ServerResponse.createByErrorMessage("用户不存在");
        Product product = productService.getProductById(productId);
        if (product == null) return ServerResponse.createByErrorMessage("产品不存在");
        return productService.addRemark(userId, productId, remark, rate);
    }
}
