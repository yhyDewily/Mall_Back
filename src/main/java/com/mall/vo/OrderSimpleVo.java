package com.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderSimpleVo {

    private Long orderNo;
    private String recipient;
    private Integer status;
    private String status_desc;
    private BigDecimal payment;
    private String createTime;
}
