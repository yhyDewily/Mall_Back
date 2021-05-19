package com.mall.repository;

import com.mall.dataobject.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * from mall.product where id=?1", nativeQuery = true)
    Product findByProductId(Integer productId);

    List<Product> findAllById(Integer productId, Pageable pageable);

    @Query(value = "SELECT * from mall.product", nativeQuery = true)
    List<Product> findAllProduct();

    @Query(value = "select * from mall.product where mall.product.category_id in (select mall.category.id from mall.category where mall.category.parent_id = ?1)", nativeQuery = true)
    Page<Product> findProductBySex(Integer categoryId, Pageable pageable);

    @Query(value = "select * from mall.product where mall.product.category_id in (select mall.category.id from mall.category where mall.category.parent_id = ?1)", nativeQuery = true)
    List<Product> findProductBySex(Integer categoryId);

//    @Query(value = "SELECT * from mall.product", nativeQuery = true)
//    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllByGrandId(Integer grandId, Pageable pageable);

    List<Product> findByCategoryId(Integer categoryId);

    Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);

    @Query(value = "select * from mall.product ORDER BY sold DESC limit 20 ", nativeQuery = true)
    List<Product> findBySoldHot();

    @Query(value = "select * from mall.product where category_id=?1 order by hits desc limit 5", nativeQuery = true)
    List<Product> findByHitsHotAndCategory(Integer categoryId);

    @Query(value = "select * from mall.product order by hits limit 20", nativeQuery = true)
    List<Product> findMostHts();
}
