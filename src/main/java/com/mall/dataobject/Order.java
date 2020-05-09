package com.mall.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "mall_order")
public class Order {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "order_no")
  private Long orderNo;

  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "address_id")
  private Integer addressId;

  private BigDecimal payment;

  @Column(name = "payment_type")
  private Integer paymentType;

  private Integer status;

  @Column(name = "payment_time")
  private Date paymentTime;

  @Column(name = "send_time")
  private Date sendTime;

  @Column(name = "end_time")
  private Date endTime;

  @Column(name = "close_time")
  private Date closeTime;

  @Column(name = "create_time")
  private Date createTime;
}
