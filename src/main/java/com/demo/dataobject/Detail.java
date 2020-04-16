package com.demo.dataobject;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "detail")
@Data
public class Detail {
    @Id
    @Column(name = "id")
    private Integer id;

    private Integer product_id;

    private String detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Detail() {
    }

    public Detail(Integer id, Integer product_id, String detail) {
        this.id = id;
        this.product_id = product_id;
        this.detail = detail;
    }

}
