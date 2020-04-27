package com.mall.vo;

import lombok.Data;

@Data
public class AliPayVo {

    //商户订单号
    private String outTradeNo;
    //交易状态
    private String tradeStatus;
    //交易流水号
    private String tradeNo;
    //资金总额
    private String totalAmount;
    //卖家支付宝账户
    private String sellerId;
    //买家支付宝账户
    private String buyerLogoinId;
    //签名方式
    private String signType;
    //签名
    private String sign;


}
