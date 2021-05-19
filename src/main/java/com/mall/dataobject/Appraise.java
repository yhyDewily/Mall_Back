package com.mall.dataobject;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

//商品评论类
@Data
@Entity
@Table(name = "appraise")
public class Appraise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer user_id;

    private Integer product_id;

    private String content;

    private Integer rate;

    private Date create_time;
}
