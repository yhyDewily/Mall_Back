package com.mall.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartProductVo {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("productId")
    private Integer productId;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("productSubtitle")
    private String productSubtitle;

    @JsonProperty("productPrice")
    private BigDecimal productPrice;

    @JsonProperty("productTotalPrice")
    private BigDecimal productTotalPrice;

    @JsonProperty("productStock")
    private Integer productStock;

    @JsonProperty("productChecked")
    private Integer productChecked;

    @JsonProperty("productMainImage")
    private String productMainImage;

    @JsonProperty("limitQuantity")
    private String limitQuantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public Integer getProductChecked() {
        return productChecked;
    }

    public void setProductChecked(Integer productChecked) {
        this.productChecked = productChecked;
    }

    public String getProductMainImage() {
        return productMainImage;
    }

    public void setProductMainImage(String productMainImage) {
        this.productMainImage = productMainImage;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
    }

    public CartProductVo() {
    }

    public CartProductVo(Integer id, Integer productId, Integer userId, Integer quantity, String productName, String productSubtitle, BigDecimal productPrice, BigDecimal productTotalPrice, Integer productStock, Integer productChecked, String productMainImage, String limitQuantity) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
        this.productName = productName;
        this.productSubtitle = productSubtitle;
        this.productPrice = productPrice;
        this.productTotalPrice = productTotalPrice;
        this.productStock = productStock;
        this.productChecked = productChecked;
        this.productMainImage = productMainImage;
        this.limitQuantity = limitQuantity;
    }
}
