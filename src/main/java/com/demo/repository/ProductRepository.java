package com.demo.repository;

import com.demo.dataobject.Product;
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

    @Query(value = "SELECT * from mall.product", nativeQuery = true)
    List<Product> findAllProduct();

    @Query(value = "select * from mall.product where mall.product.category_id in (select mall.category.id from mall.category where mall.category.parent_id = ?1)", nativeQuery = true)
    List<Product> findProductBySex(Integer categoryId, Pageable pageable);

//    @Query(value = "SELECT * from mall.product", nativeQuery = true)
//    Page<Product> findAll(Pageable pageable);
    Page<Product> findAllByGrandId(Integer grandId, Pageable pageable);


}
