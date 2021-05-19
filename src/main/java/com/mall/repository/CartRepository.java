package com.mall.repository;

import com.mall.dataobject.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByUserId(Integer useId);

    @Query(value = " SELECT  count(1) from mall.cart where checked = 0 and user_id = ?1", nativeQuery = true)
    Integer selectCartProductCheckedStatusByUserId(Integer userId);

    Cart findByIdAndUserId(Integer id, Integer userId);

    @Transactional
    void deleteByUserIdAndProductId(Integer userId, Integer productId);

    Cart findByUserIdAndProductId(Integer userId, Integer productId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE mall.cart set checked = ?2 where user_id=?1", nativeQuery = true)
    void checkedOrUncheckedProductAll(Integer userId, int checked);

    @Modifying
    @Transactional
    @Query(value = "UPDATE mall.cart set checked = ?3 where user_id=?1 and product_id=?2", nativeQuery = true)
    void checkedOrUncheckedProduct(Integer userId, int ProductId, int checked);

    @Query(value = "SELECT quantity from mall.cart where user_id=?1 and product_id=?2", nativeQuery = true)
    int getQuantityByUserIdAndProductId(Integer userId, Integer productId);

    @Query(value = "SELECT * FROM mall.cart where user_id=? and checked=1", nativeQuery = true)
    List<Cart> selectCheckedCartByUserId(Integer userId);

    void deleteById(Integer id);
}
