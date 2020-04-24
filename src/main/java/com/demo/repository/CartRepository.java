package com.demo.repository;

import com.demo.dataobject.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByUserId(Integer useId);

    @Query(value = " SELECT  count(1) from mall.cart where checked = 0 and user_id = ?1", nativeQuery = true)
    Integer selectCartProductCheckedStatusByUserId(Integer userId);

    Cart findByIdAndUserId(Integer id, Integer userId);

    void deleteByUserIdAndProductId(Integer userId, Integer productId);

    Cart findByUserIdAndProductId(Integer userId, Integer productId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE mall.cart set checked = ?2,update_time = now() where user_id=?1", nativeQuery = true)
    void checkedOrUncheckedProductAll(Integer userId, int checked);

    @Modifying
    @Transactional
    @Query(value = "UPDATE mall.cart set checked = ?3,update_time = now() where user_id=?1 and product_id=?2", nativeQuery = true)
    void checkedOrUncheckedProduct(Integer userId, int ProductId, int checked);
}
