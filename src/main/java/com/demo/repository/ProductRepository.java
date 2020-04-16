package com.demo.repository;

import com.demo.dataobject.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * from mall.product where id=?1", nativeQuery = true)
    Product findByProductId(Integer productId);

    @Query(value = "SELECT * from mall.product", nativeQuery = true)
    List<Product> findAll();

    @Query(value = "select * from mall.product where mall.product.category_id in (select mall.catergory.id from mall.category where  mall.catergory.parent_id = ?1)", nativeQuery = true)
    List<Product> findProductBySex(Integer parentId);
}
