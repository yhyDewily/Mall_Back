package com.demo.controller.portal;

import com.demo.common.ServerResponse;
import com.demo.dataobject.Product;
import com.demo.service.Impl.ProductServiceImpl;
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
    public ServerResponse<Product> getProductInfo(Integer productId) {
        Product product = productService.getProductInfo(productId);
        if (product != null) return ServerResponse.createBySuccess(product);
        return ServerResponse.createByErrorMessage("产品不存在");
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
}
