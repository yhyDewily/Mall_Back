package com.demo.dataobject;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "Product")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_id")
    private Integer categoryId;

    private String name;

    private String subtitle;

    @Column(name = "main_image")
    private String mainImage;

    @Column(name = "sub_images")
    private String subImages;

    private String detail;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

    private Integer sold;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    private Date create_Time;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    private Date update_Time;

    public Product(Integer id, Integer categoryId, String name, String subtitle, String mainImage, String subImages, String detail, BigDecimal price, Integer stock, Integer sold, Integer status, Date create_Time, Date update_Time) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.subtitle = subtitle;
        this.mainImage = mainImage;
        this.subImages = subImages;
        this.detail = detail;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.create_Time = create_Time;
        this.update_Time = update_Time;
        this.sold = sold;
    }

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreate_Time() {
        return create_Time;
    }

    public void setCreate_Time(Date createTime) {
        this.create_Time = createTime;
    }

    public Date getUpdate_Time() {
        return update_Time;
    }

    public void setUpdate_Time(Date updateTime) {
        this.update_Time = updateTime;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }
}
