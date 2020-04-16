package com.demo.dataobject;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "grand")
@Data
public class Grand {

    @Id
    @Column(name = "id")
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Grand() {
    }

    public Grand(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
