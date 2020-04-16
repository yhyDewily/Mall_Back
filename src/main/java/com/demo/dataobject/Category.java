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
@Table(name = "category")
@Data
public class Category {
    @Id
    @Column(name = "id")
    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @CreatedDate
    private Date create_Time;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @CreatedDate
    private Date update_Time;

    public Category(Integer id, Integer parentId, String name, Boolean status, Integer sortOrder, Date create_Time, Date update_Time) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.status = status;
        this.sortOrder = sortOrder;
        this.create_Time = create_Time;
        this.update_Time = update_Time;
    }

    public Category() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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
