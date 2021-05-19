package com.mall.dataobject;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "shopping_similarity")
@Entity
public class UserSimilarityDTO implements Serializable, Comparable<UserSimilarityDTO> {

    private static final long serialVersionUID = 3940726816248544380L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_ref_id")
    private Integer userReId;
    private Double similarity;

    @Override
    public int compareTo(UserSimilarityDTO o) {
        return o.getSimilarity().compareTo(this.getSimilarity());
    }
}
