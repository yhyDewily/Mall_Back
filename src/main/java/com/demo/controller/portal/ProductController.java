package com.demo.controller.portal;

import com.demo.common.ServerResponse;
import com.demo.dataobject.Product;
import com.demo.service.Impl.ProductServiceImpl;
import com.demo.service.ProductService;
import com.demo.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ServerResponse<Product> getProductInfo(Integer productId) {
        Product product = productService.getProductInfo(productId);
        if (product != null) return ServerResponse.createBySuccess(product);
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
