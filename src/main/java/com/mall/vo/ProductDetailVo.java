package com.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDetailVo {

    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImages;
    private String detail;
    private String grand;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private Integer parentCategoryId;
}
