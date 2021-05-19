package com.mall.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "shopping_active")
@Entity
public class UserActiveDTO implements Serializable {

    private static final long serialVersionUID = -103797500202536441L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "category2_id")
    private Integer category2Id;
    private Long hits;

}
