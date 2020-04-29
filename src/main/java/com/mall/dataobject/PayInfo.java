package com.mall.dataobject;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pay_info")
@Data
public class PayInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "pay_platform")
    private Integer payPlatform;

    @Column(name = "platform_number")
    private String platformNumber;

    @Column(name = "platform_status")
    private String platformStatus;
}
