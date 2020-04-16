package com.demo.dataobject;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "cart")
@Data
public class Cart {
    @Id
    @Column(name = "id")
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private Integer checked;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @CreatedDate
    private Date create_Time;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @CreatedDate
    private Date update_Time;

    public Cart(Integer id, Integer userId, Integer productId, Integer quantity, Integer checked, Date create_Time, Date update_Time) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.checked = checked;
        this.create_Time = create_Time;
        this.update_Time = update_Time;
    }

    public Cart() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
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
}
