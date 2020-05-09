package com.mall.controller.portal;

import com.mall.common.GrandUtil;
import com.mall.common.ServerResponse;
import com.mall.dataobject.Product;
import com.mall.service.Impl.ProductServiceImpl;
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

    @RequestMapping(value = "get_product_info.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ServerResponse<ProductVo> getProductInfo(Integer productId) {
        Product product = productService.getProductById(productId);
        if (product != null) return ServerResponse.createBySuccess(productService.getProductInfo(productId));
        return ServerResponse.createByErrorMessage("产品不存在");
    }

    @RequestMapping(value = "detail.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return productService.getProductDetail(productId);
    }


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
}
