package com.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductVo {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private List<String> subImages;
    private String detail;
    private List<String> params;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private Integer grandId;
}
