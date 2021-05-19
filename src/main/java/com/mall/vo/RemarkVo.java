package com.mall.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RemarkVo {
    private String userName;
    private String content;
    private Date create_time;
    private Integer rate;
}
